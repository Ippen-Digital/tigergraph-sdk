package de.id.tigergraph.sdk.studio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphStudioResponse {

	@JsonProperty("error")
	private boolean error;

	@JsonProperty("message")
	private String message;

}
