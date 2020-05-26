package com.rockstar.artifact.converter.swagger;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.FieldDefinition;

import io.swagger.models.properties.Property;

@Component
public class SwaggerPropertyToFieldDefinition implements Converter<Property, FieldDefinition> {

	@Inject SwaggerPropertyTypeToFieldType swaggerPropertyTypeToFieldType;
	
	public FieldDefinition convert(Property schema) {
		FieldDefinition fieldDefinition = null;
		
		if (schema != null) {
			fieldDefinition = new FieldDefinition();
			fieldDefinition.setType(this.swaggerPropertyTypeToFieldType.convert(schema));
			fieldDefinition.setReadOnly(schema.getReadOnly());
			/*if (schema.get()) {
				fieldDefinition.setEnumeration(true);
			}*/
		}
		return fieldDefinition;
	}

}