package com.rockstar.artifact.converter.swagger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rockstar.artifact.codegen.model.AttributeDefinition;
import com.rockstar.artifact.codegen.model.ConstraintType;
import com.rockstar.artifact.codegen.model.ResourceDefinition;

import io.swagger.models.Model;
import io.swagger.models.properties.Property;

@Component
public class SwaggerModelToResourceDefinition implements Converter<Model, ResourceDefinition>{
	
	@Inject SwaggerPropertyToAttributeDefinition swaggerPropertyToAttributeDefinition;
	
	public ResourceDefinition convert(Model schema) {
		ResourceDefinition resource = null;
		Collection<AttributeDefinition> attributes = null;
		AttributeDefinition attribute = null;
		Collection<String> requiredfields = null;
		Property propertySchema = null;
		String propertyName = null;
		
		if (schema != null) {
			resource = new ResourceDefinition();
			
			if (schema.getProperties() != null && !schema.getProperties().isEmpty()) {
				attributes = new ArrayList<AttributeDefinition> ();
				for (Entry<String, Property> schemaEntry : schema.getProperties().entrySet()) {
					propertyName = schemaEntry.getKey();
					propertySchema = schemaEntry.getValue();
					attribute = this.swaggerPropertyToAttributeDefinition.convert(propertySchema);
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
