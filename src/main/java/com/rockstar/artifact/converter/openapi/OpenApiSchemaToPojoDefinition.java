package com.rockstar.artifact.converter.openapi;

import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.jsonoverlay.Reference;
import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.PojoDefinition;
import com.rockstar.artifact.codegen.model.PropertyDefinition;

@Component
public class OpenApiSchemaToPojoDefinition implements Converter<Schema, PojoDefinition>{

	public PojoDefinition convert(Schema schema) {
		PojoDefinition pojoDefinition = null;
		
		if (schema != null) {
			pojoDefinition = new PojoDefinition();
			pojoDefinition.setName(schema.getTitle());
			for (Entry<String, Schema> schemaEntry : schema.getProperties().entrySet()) {
				pojoDefinition.addPropertyDefinition(schemaEntry.getKey(), this.toPropertyDefinition(schemaEntry.getKey(), schemaEntry.getValue()));
			}
		}
		return pojoDefinition;
	}
	
	private PropertyDefinition toPropertyDefinition(String name, Schema propertySchema) {
		PropertyDefinition propertyDefinition = null;
		
		propertyDefinition = new PropertyDefinition();
		propertyDefinition.setName(name);
		propertyDefinition.setType(propertySchema.getType());
		propertyDefinition.setSampleData(propertySchema.getExample());
		if (propertySchema.getUniqueItems() != null && propertySchema.getUniqueItems()) {
			propertyDefinition.setUnique(true);
		}
		if (propertySchema.hasEnums()) {
			propertyDefinition.setEnumeration(true);
			propertyDefinition.setAllowableValues(propertySchema.getEnums());
		}
		if (propertySchema.getType().equalsIgnoreCase("array")) {
			Reference itemSchemaReference = propertySchema.getItemsSchemaReference();
			if (itemSchemaReference != null) {
				propertyDefinition.setReference(StringUtils.substringAfterLast(itemSchemaReference.getKey(), "/"));
			}
		}
		/**
		if (propertySchema.getType().equalsIgnoreCase("object")) {
			Reference itemSchemaReference = propertySchema.getItemsSchemaReference();
			if (itemSchemaReference != null) {
				propertyDefinition.setReference(StringUtils.substringAfterLast(itemSchemaReference.getKey(), "/"));
			}
		}
		*/
		
		return propertyDefinition;
	}
	

}
