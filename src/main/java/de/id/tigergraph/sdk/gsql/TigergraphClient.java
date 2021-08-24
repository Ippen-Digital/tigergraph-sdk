package de.id.tigergraph.sdk.gsql;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * wrapper around the original GSQL Client to provide a unified interface and allow us to initialize different versions its a result because they dont use interfaces or abstract
 * classes
 * 
 * extends the original `com.tigergraph.<version>.client.Client` class and adds unified methods
 */
public interface TigergraphClient {

	public void login();

	public void start() throws Exception;

	public String executeCommand(String command) throws UnsupportedEncodingException;

	/**
	 * catches tigergraph gsql server response and outputs it as a string this should be a method inside an abstract but sadly this is not possible due to the clients structure
	 * 
	 * @param bulder
	 * @param response
	 * @return {@see String}
	 */
	default void parseResponse(StringBuilder builder, InputStream response) {
		Scanner sc = new Scanner(response, StandardCharsets.UTF_8.name());
		String line;
		String progressBarPattern = "\\[=*\\s*\\]\\s[0-9]+%.*";

		while (sc.hasNextLine()) {
			line = sc.nextLine();
			if (line.startsWith("__GSQL__RETURN__CODE__") ||
					line.startsWith("__GSQL__INTERACT__") ||
					line.startsWith("__GSQL__MOVE__CURSOR___UP__")) {
				// break when
				// return code was received
				// server wants to interact, never use interactive code with this!
				// end of line is reached
				break;
			} else if (line.startsWith("__GSQL__COOKIES__") ||
					line.startsWith("__GSQL__CLEAN__LINE__") ||
					line.matches(progressBarPattern)) {
				// continue when
				// line should be cleared
				// cookies should be set
				// its a progressbar
			} else {
				// otherwise capture the line
				builder.append(line).append(System.getProperty("line.separator"));
			}
		}
		sc.close();
	}

	/**
	 * this is an old method and marked for removal in the future it might not be used already
	 * 
	 * @param graph
	 * @return
	 */
	@Deprecated(forRemoval = true)
	boolean switchGraph(String graph);
}
