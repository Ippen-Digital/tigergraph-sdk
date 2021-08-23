package de.id.tigergraph.sdk.studio;

import org.springframework.web.client.RestTemplate;

import de.id.tigergraph.sdk.model.GraphSchema;
import java.net.URI;

import org.springframework.lang.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public class StudioTemplate extends RestTemplate implements StudioOperations {

	private URI baseUri;

	@Override
	@Nullable
	public GraphSchema getGraphSchema(String graph) {
		String url = UriComponentsBuilder
				.fromUri(this.baseUri)
				.queryParam("graph", graph)
				.toUriString();

		ResponseEntity<GraphSchema> schema = this.getForEntity(url, GraphSchema.class);
		if (schema.getStatusCode() == HttpStatus.OK) {
			return schema.getBody();
		}
		return null;
	}

}
