package com.rockstar.artifact.converter.jsonschema.draft06;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rockstar.artifact.codegen.definition.PrimitiveType;
import com.rockstar.artifact.codegen.definition.PropertyDefinition;

@Component
public class JsonSchemaToPropertyDefinition implements Converter<Map<String, Object>, PropertyDefinition> {

	@Autowired JsonSchemaToConstraintDefinition jsonSchemaToConstraintDefinition;
	@Autowired JsonSchemaToPrimitiveType jsonSchemaToPrimitiveType;
	
	public PropertyDefinition convert(Map<String, Object> schema) {
		PropertyDefinition propertyDefinition = null;
		PrimitiveType primitiveType = null;
		
		if (schema != null) {
			primitiveType = this.jsonSchemaToPrimitiveType.convert(schema);
			propertyDefinition = new PropertyDefinition();
			propertyDefinition.setType(primitiveType);
			if (schema.containsKey("uniqueItems")) {
				propertyDefinition.setUnique((Boolean) schema.get("uniqueItems"));
			}
			if (schema.containsKey("default")) {
				propertyDefinition.setDefaultValue(schema.get("default"));
			}
			propertyDefinition.setConstraints(this.jsonSchemaToConstraintDefinition.convert(schema));
		}
		
		return propertyDefinition;
	}

}
