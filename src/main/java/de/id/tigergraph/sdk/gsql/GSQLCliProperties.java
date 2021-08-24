package de.id.tigergraph.sdk.gsql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ConfigurationProperties("exporter.gsql")
public class GSQLCliProperties {

	private String username;
	private String password;
	private String[] graphs;
	private String defaultGraph;

	private String domain;
	private int port;

	public String[] toArgs() {
		List<String> arguments = new ArrayList<>();
		arguments.add(String.format("-u%s", this.username));
		arguments.add(String.format("-p%s", this.password));
		arguments.add(String.format("-g%s", this.defaultGraph));
		arguments.add("--graphstudio");
		return arguments.toArray(String[]::new);
	}

	public int getPort() {
		if (this.port == 0) {
			// default port
			return 14240;
		}
		return this.port;
	}

	public void setGraphs(String... aGraphs) {
		this.graphs = aGraphs;
	}

	public String getDefaultGraph() {
		if (this.defaultGraph == null) {
			setDefaultGraph(this.graphs[0]);
		}
		return this.defaultGraph;
	}
}
