package de.id.tigergraph.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Edge {

	@JsonProperty("IsDirected")
	private boolean directed;

	@JsonProperty("ToVertexTypeName")
	private String toVertex;

	@JsonProperty("Config")
	private Map<String, String> config;

	@JsonProperty("Attributes")
	private String attributes;

	@JsonProperty("FromVertexTypeName")
	private String fromVertex;

	@JsonProperty("Name")
	private String name;
}
