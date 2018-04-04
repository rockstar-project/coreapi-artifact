package com.rockstar.artifact.converter.swagger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rockstar.artifact.codegen.model.AttributeType;

import io.swagger.models.properties.Property;

@Component
public class SwaggerPropertyTypeToAttributeType implements Converter<Property, AttributeType> {

	public AttributeType convert(Property schema) {
		String typeStr = null;
		String formatStr = null;
		AttributeType attributeType = AttributeType.String;
		
		typeStr = schema.getType();
		formatStr = schema.getFormat();
		
		if (StringUtils.isNotEmpty(typeStr)) {
			switch (typeStr) {
				case "string":
					if (formatStr != null) {
						switch (formatStr) {
							case "date":
								attributeType = AttributeType.LocalDate;
								break;
							case "date-time":
								attributeType = AttributeType.DateTime;
								break;
							case "uuid":
								attributeType = AttributeType.UUID;
								break;
							case "byte":
							case "binary":
								attributeType = AttributeType.Byte;
								break;
							default:
								attributeType = AttributeType.String;
								break;
						}
					} else {
						attributeType = AttributeType.String;
					}
					break;
				case "integer":
					if (formatStr != null) {
						switch (formatStr) {
							case "int32":
								attributeType = AttributeType.Integer;
								break;
							case "int64":
								attributeType = AttributeType.Long;
								break;
							default:
								attributeType = AttributeType.Integer;
								break;
						}
					} else {
						attributeType = AttributeType.Integer;
					}
					break;
				case "number":
					if (formatStr != null) {
						switch (formatStr) {
							case "float":
								attributeType = AttributeType.Float;
								break;
							case "double":
								attributeType = AttributeType.Double;
								break;
							default:
								attributeType = AttributeType.Double;
								break;
						}
					} else {
						attributeType = AttributeType.Double;
					}
					break;
				case "boolean":
					attributeType = AttributeType.Boolean;
					break;
				case "object":
					attributeType = AttributeType.Object;
					break;
				case "array":
					attributeType = AttributeType.Array;
					break;
				default:
					attributeType = AttributeType.String;
					break;
			}
		}
		return attributeType;
	} 

}
