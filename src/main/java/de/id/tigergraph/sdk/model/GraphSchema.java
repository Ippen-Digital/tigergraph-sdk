package de.id.tigergraph.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.id.tigergraph.sdk.studio.GraphStudioResponse;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphSchema extends GraphStudioResponse {

	@JsonProperty("results.GraphName")
	private String graphName;

	@JsonProperty("results.VertexTypes")
	private List<Vertex> vertices;

	@JsonProperty("results.EdgeTypes")
	private List<Edge> edges;

}
