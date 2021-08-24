package de.id.tigergraph.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribute {

	@JsonProperty("AttributeType.Name")
	private String attributeType;

	@JsonProperty("IsPartOfCompositeKey")
	private boolean compositeKey;

	@JsonProperty("PrimaryIdAsAttribute")
	private boolean primaryAsAttribute;

	@JsonProperty("HasIndex")
	private boolean index;

	@JsonProperty("IsPrimaryKey")
	private boolean primaryKey;

	@JsonProperty("AttributeName")
	private String name;
}
