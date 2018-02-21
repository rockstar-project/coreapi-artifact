package com.rockstar.artifact.converter.openapi;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.AttributeDefinition;

@Component
public class OpenApiSchemaToAttributeDefinition implements Converter<Schema, AttributeDefinition> {

	@Inject OpenApiTypeToAttributeType openApiTypeToAttributeType;
	@Inject OpenApiSchemaToConstraintDefinition openApiSchemaToConstraintDefinition;
	
	public AttributeDefinition convert(Schema schema) {
		AttributeDefinition attributeDefinition = null;
		
		if (schema != null) {
			attributeDefinition = new AttributeDefinition();
			attributeDefinition.setType(this.openApiTypeToAttributeType.convert(schema));
			attributeDefinition.setConstraints(this.openApiSchemaToConstraintDefinition.convert(schema));
		}
		return attributeDefinition;
	}

}
