
package de.id.tigergraph.sdk.gsql.condition;

import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnTigergraphVersionCondition extends SpringBootCondition {

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnTigergraphVersion.class.getName());
		if (attributes == null) {
			return ConditionOutcome.noMatch("No attributes found");
		}
		String version = (String) attributes.get("value");
		boolean matchMissing = (boolean) attributes.get("matchMissing");

		return getMatchOutcome(context.getEnvironment(), version, matchMissing);
	}

	private ConditionOutcome getMatchOutcome(Environment environment, String versionString, boolean matchMissing) {
		String version = environment.getProperty("exporter.tigergraph.version", "NA");

		ConditionMessage.Builder message = ConditionMessage.forCondition(ConditionalOnTigergraphVersion.class);

		if (version.equals(versionString)) {
			// got match
			return ConditionOutcome.match(message.foundExactly(versionString));
		}

		if ("NA".equals(version) && matchMissing) {
			// ok we got a match, even though nothing was given
			return ConditionOutcome.match(message.resultedIn(version));
		}
		// no match, this can happen even if e.g 10.2 was given but there is none
		return ConditionOutcome.noMatch(message.didNotFind(version).atAll());
	}
}
