package de.id.tigergraph.sdk.restpp;

import java.net.URI;
import java.util.List;
import de.id.tigergraph.sdk.model.Vertex;

import org.springframework.web.client.RestTemplate;

public class RestPPTemplate extends RestTemplate implements RestPPOperations {

	private URI baseUri;

	@Override
	public List<Vertex> listVerticesForGraph(String graph, String vertexType) {
		// TODO Auto-generated method stub
		return null;
	}

}
