package com.rockstar.artifact.converter.swagger;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rockstar.artifact.codegen.model.AttributeDefinition;
import com.rockstar.artifact.codegen.model.AttributeType;

import io.swagger.models.properties.Property;

@Component
public class SwaggerPropertyToAttributeDefinition implements Converter<Property, AttributeDefinition> {

	@Inject SwaggerPropertyTypeToAttributeType swaggerPropertyTypeToAttributeType;
	@Inject SwaggerPropertyToConstraintDefinition swaggerPropertyToConstraintDefinition;
	
	public AttributeDefinition convert(Property schema) {
		AttributeDefinition attributeDefinition = null;
		AttributeType attributeType = null;
		if (schema != null) {
			attributeType = this.swaggerPropertyTypeToAttributeType.convert(schema);
			attributeDefinition = new AttributeDefinition();
			attributeDefinition.setType(attributeType);
			attributeDefinition.setConstraints(this.swaggerPropertyToConstraintDefinition.convert(schema));
		}
		return attributeDefinition;
	}

}
