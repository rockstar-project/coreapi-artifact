package com.rockstar.artifact.converter.openapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.AttributeDefinition;
import com.rockstar.artifact.codegen.model.ConstraintType;
import com.rockstar.artifact.codegen.model.ResourceDefinition;

@Component
public class OpenApiSchemaToResourceDefinition implements Converter<Schema, ResourceDefinition>{
	
	@Inject OpenApiSchemaToAttributeDefinition openApiSchemaToAttributeDefinition;
	
	public ResourceDefinition convert(Schema schema) {
		ResourceDefinition resource = null;
		Collection<AttributeDefinition> attributes = null;
		AttributeDefinition attribute = null;
		Collection<String> requiredfields = null;
		Schema propertySchema = null;
		String propertyName = null;
		
		if (schema != null) {
			resource = new ResourceDefinition();
			requiredfields = schema.getRequiredFields();
			if (schema.getProperties() != null && !schema.getProperties().isEmpty()) {
				attributes = new ArrayList<AttributeDefinition> ();
				for (Entry<String, Schema> schemaEntry : schema.getProperties().entrySet()) {
					propertyName = schemaEntry.getKey();
					propertySchema = schemaEntry.getValue();
					attribute = this.openApiSchemaToAttributeDefinition.convert(propertySchema);
					attribute.setName(propertyName);
					if (requiredfields != null && requiredfields.contains(propertyName)) {
						if (propertySchema.getType().equalsIgnoreCase("object")) {
							attribute.addConstraint(ConstraintType.NotNull);
						} else {
							attribute.addConstraint(ConstraintType.NotEmpty);
						}
					}
					attributes.add(attribute);
				}
				resource.setAttributes(attributes);
			}
		}
		return resource;
	}
}
