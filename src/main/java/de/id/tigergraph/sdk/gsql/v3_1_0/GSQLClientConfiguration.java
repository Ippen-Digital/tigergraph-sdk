package de.id.tigergraph.sdk.gsql.v3_1_0;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.id.tigergraph.sdk.gsql.GSQLCliProperties;
import de.id.tigergraph.sdk.gsql.TigergraphClient;
import de.id.tigergraph.sdk.gsql.condition.ConditionalOnTigergraphVersion;

@Configuration
@EnableConfigurationProperties(GSQLCliProperties.class)
@ConditionalOnTigergraphVersion(value = "3.1.0", matchMissing = true)
public class GSQLClientConfiguration {

	@Bean
	public Map<String, TigergraphClient> gsqlClient310(GSQLCliProperties props) {
		return Arrays.asList(props.getGraphs())
				.stream()
				.collect(Collectors.toMap(e -> e, e -> GSQLClient.newInstance(props, e)));
	}
}
