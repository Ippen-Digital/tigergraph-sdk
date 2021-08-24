
package de.id.tigergraph.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vertex {

	@JsonProperty("Config")
	private Map<String, String> config;

	@JsonProperty("PrimaryId")
	private Attribute primaryID;

	@JsonProperty("Name")
	private String name;

	@JsonProperty("Attributes")
	private List<Attribute> attributes;

}
