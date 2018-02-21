package com.rockstar.artifact.converter.openapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.AttributeDefinition;
import com.rockstar.artifact.codegen.model.ResourceDefinition;

@Component
public class OpenApiSchemaToResourceDefinition implements Converter<Schema, ResourceDefinition>{
	
	@Inject OpenApiSchemaToAttributeDefinition openApiSchemaToAttributeDefinition;
	
	public ResourceDefinition convert(Schema schema) {
		ResourceDefinition resource = null;
		Collection<ResourceDefinition> subresources = null;
		Collection<AttributeDefinition> attributes = null;
		AttributeDefinition attribute = null;
		Schema propertySchema = null;
		String propertyType = null;
		String propertyName = null;
		String subresourceName = null;
		
		if (schema != null) {
			resource = new ResourceDefinition();
			attributes = new ArrayList<AttributeDefinition> ();
			subresources = new ArrayList<ResourceDefinition> ();
			if (schema.getProperties() != null && !schema.getProperties().isEmpty()) {
				for (Entry<String, Schema> schemaEntry : schema.getProperties().entrySet()) {
					propertyName = schemaEntry.getKey();
					propertySchema = schemaEntry.getValue();
					propertyType = propertySchema.getType();
					
					switch (propertyType) {
						case "object":
							// TODO
							break;
						case "array":
							Schema itemsSchema = propertySchema.getItemsSchema();
							if (itemsSchema != null) {
								ResourceDefinition subresourceDefinition = this.convert(propertySchema.getItemsSchema());
								subresourceName = StringUtils.substringAfterLast(propertySchema.getItemsSchemaReference().getKey(), "/");
								subresourceDefinition.setName(subresourceName);
								subresources.add(subresourceDefinition);
							}
							break;
						default:
							attribute = this.openApiSchemaToAttributeDefinition.convert(propertySchema);
							attribute.setName(propertyName);
							attributes.add(attribute);
							break;
					}
				}
				
			}
			if (!attributes.isEmpty()) {
				resource.setAttributes(attributes);
			}
			if (!subresources.isEmpty()) {
				resource.setSubresources(subresources);
			}
		}
		return resource;
	}
}
