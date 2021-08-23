package de.id.tigergraph.sdk.gsql.v3_1_2;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.security.Permission;
import java.util.Map;

import com.tigergraph.v3_1_2.client.Client;
import com.tigergraph.v3_1_2.client.GsqlCli;
import com.tigergraph.v3_1_2.client.util.HttpResponseOperator;
import com.tigergraph.v3_1_2.client.util.RetryableHttpConnection;

import org.springframework.util.StringUtils;

import de.id.tigergraph.sdk.gsql.GSQLCliProperties;
import de.id.tigergraph.sdk.gsql.TigergraphClient;

/**
 * extends the original `com.tigergraph.<version>.client.Client` class and adds unified methods specifiec by GSQLClient
 */
public class GSQLClient extends Client implements TigergraphClient {
	private GsqlCli cli;

	public static GSQLClient newInstance(GSQLCliProperties props, String graph) {
		props.setDefaultGraph(graph);
		GsqlCli cli = new GsqlCli();
		cli.parse(props.toArgs());
		GSQLClient client = new GSQLClient(cli);
		try {
			RetryableHttpConnection conn = new RetryableHttpConnection(false, null, 14240);
			conn.addIpPort(props.getDomain(), props.getPort());
			client.setRetryableHttpConn(conn);
		} catch (Exception e) {
			return null;
		}
		return client;
	}

	public GSQLClient(GsqlCli cli) {
		super(cli, null);
		this.cli = cli;
	}

	@Override
	public void login() {
		this.login(cli);
		Runtime
				.getRuntime()
				.addShutdownHook(new Thread(() -> sendPost("abortclientsession", "abortclientsession")));

	}

	public void start() throws Exception {
		this.start(cli);
	}

	@Override
	public String executeCommand(String command) throws UnsupportedEncodingException {
		this.login();
		String msg = URLEncoder.encode(command, "UTF-8");
		// well i can override executePost but cannot use `sendHttpRequest`
		// because its private... this seems kind of stupid as it would force me
		// to create my own logic to send a request
		// if i would to that i would need to create my own connection handling, error handling
		// and so on.. therefore its easiert to hack the method and make it accessable from this method
		// i now that this is not ideal but it lets me System.out.println statements for loading jobs
		// Note that this is only tested for 'Show LOADING STATUS ALL' and not other commands
		Method method;
		try {
			method = getClass().getSuperclass().getDeclaredMethod("sendHttpRequest", String.class, Map.class, String.class, String.class, HttpResponseOperator.class);

			if ((!Modifier.isPublic(method.getModifiers()) ||
					!Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.canAccess(this)) {
				method.setAccessible(true);
			}

			StringBuilder builder = new StringBuilder();
			method.invoke(this, "command", null, msg, "POST", (HttpResponseOperator) response -> this.parseResponse(builder, response));

			return builder.toString();
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean switchGraph(String graph) {
		if (!StringUtils.hasText(graph)) {
			return false;
		}

		String msg;
		SecurityManager current = System.getSecurityManager();
		try {
			MySecurityManager secManager = new MySecurityManager();
			System.setSecurityManager(secManager);

			msg = URLEncoder.encode("USE GRAPH " + graph, "UTF-8");
			this.executePost("/file", msg);
			// well this will never be called but my ide complains when its missing
			return true;
		} catch (UnsupportedEncodingException e) {
			// TODO: try to do sth with this stacktrace
			e.printStackTrace();
			return false;
		} catch (SecurityException e) {
			// this happens when the executePost method wants to exit
			// after a command was executed
			System.setSecurityManager(current);

			// TODO: this exception is displayed in the logs, try to prevent it
			return true;
		}
	}

	static class MySecurityManager extends SecurityManager {
		@Override
		public void checkExit(int status) {
			throw new SecurityException();
		}

		@Override
		public void checkPermission(Permission perm) {
			// Allow other activities by default
		}
	}

}
