package com.rockstar.artifact.converter.openapi;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.AttributeDefinition;
import com.rockstar.artifact.codegen.model.AttributeType;

@Component
public class OpenApiSchemaToAttributeDefinition implements Converter<Schema, AttributeDefinition> {

	@Inject OpenApiTypeToAttributeType openApiTypeToAttributeType;
	@Inject OpenApiSchemaToConstraintDefinition openApiSchemaToConstraintDefinition;
	
	public AttributeDefinition convert(Schema schema) {
		AttributeDefinition attributeDefinition = null;
		AttributeType attributeType = null;
		if (schema != null) {
			attributeType = this.openApiTypeToAttributeType.convert(schema);
			attributeDefinition = new AttributeDefinition();
			attributeDefinition.setType(attributeType);
			attributeDefinition.setConstraints(this.openApiSchemaToConstraintDefinition.convert(schema));
		}
		return attributeDefinition;
	}

}
