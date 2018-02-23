package com.rockstar.artifact.converter.openapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.AttributeDefinition;

@Component
public class OpenApiSchemaToAttributeDefinitionCollection implements Converter<Schema, Collection<AttributeDefinition>>{
	
	@Inject OpenApiSchemaToAttributeDefinition openApiSchemaToAttributeDefinition;
	
	public Collection<AttributeDefinition> convert(Schema schema) {
		Collection<AttributeDefinition> attributes = null;
		AttributeDefinition attribute = null;
		Schema propertySchema = null;
		String propertyName = null;
		
		if (schema != null) {
			attributes = new ArrayList<AttributeDefinition> ();
			if (schema.getProperties() != null && !schema.getProperties().isEmpty()) {
				for (Entry<String, Schema> schemaEntry : schema.getProperties().entrySet()) {
					propertyName = schemaEntry.getKey();
					propertySchema = schemaEntry.getValue();
					attribute = this.openApiSchemaToAttributeDefinition.convert(propertySchema);
					attribute.setName(propertyName);
					attributes.add(attribute);
				}
			}
		}
		return attributes;
	}
}
