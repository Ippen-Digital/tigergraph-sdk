package de.id.tigergraph.sdk.restpp;

import java.util.List;
import de.id.tigergraph.sdk.model.Vertex;

public interface RestPPOperations {

	List<Vertex> listVerticesForGraph(String graph, String vertexType);

}
