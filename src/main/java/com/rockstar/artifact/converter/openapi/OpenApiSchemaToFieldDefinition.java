package com.rockstar.artifact.converter.openapi;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.FieldDefinition;

@Component
public class OpenApiSchemaToFieldDefinition implements Converter<Schema, FieldDefinition> {

	@Inject OpenApiTypeToFieldType openApiTypeToFieldType;
	
	public FieldDefinition convert(Schema schema) {
		FieldDefinition fieldDefinition = null;
		
		if (schema != null) {
			fieldDefinition = new FieldDefinition();
			fieldDefinition.setType(this.openApiTypeToFieldType.convert(schema));
			fieldDefinition.setReadOnly(schema.getReadOnly());
		}
		return fieldDefinition;
	}

}