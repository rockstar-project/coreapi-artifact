package com.rockstar.artifact.converter.swagger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rockstar.artifact.codegen.model.FieldType;

import io.swagger.models.properties.Property;

@Component
public class SwaggerPropertyTypeToFieldType implements Converter<Property, FieldType> {

	public FieldType convert(Property schema) {
		String typeStr = null;
		String formatStr = null;
		FieldType fieldType = FieldType.String;
		
		typeStr = schema.getType();
		formatStr = schema.getFormat();
		
		if (StringUtils.isNotEmpty(typeStr)) {
			switch (typeStr) {
				case "string":
					if (formatStr != null) {
						switch (formatStr) {
							case "date":
								fieldType = FieldType.LocalDate;
								break;
							case "date-time":
								fieldType = FieldType.DateTime;
								break;
							case "uuid":
								fieldType = FieldType.UUID;
								break;
							case "byte":
							case "binary":
								fieldType = FieldType.Byte;
								break;
							default:
								fieldType = FieldType.String;
								break;
						}
					} else {
						fieldType = FieldType.String;
					}
					break;
				case "integer":
					if (formatStr != null) {
						switch (formatStr) {
							case "int32":
								fieldType = FieldType.Integer;
								break;
							case "int64":
								fieldType = FieldType.Long;
								break;
							default:
								fieldType = FieldType.Integer;
								break;
						}
					} else {
						fieldType = FieldType.Integer;
					}
					break;
				case "number":
					if (formatStr != null) {
						switch (formatStr) {
							case "float":
								fieldType = FieldType.Float;
								break;
							case "double":
								fieldType = FieldType.Double;
								break;
							default:
								fieldType = FieldType.Double;
								break;
						}
					} else {
						fieldType = FieldType.Double;
					}
					break;
				case "boolean":
					fieldType = FieldType.Boolean;
					break;
				case "object":
					fieldType = FieldType.Object;
					break;
				case "array":
					fieldType = FieldType.Array;
					break;
				default:
					fieldType = FieldType.String;
					break;
			}
		}
		return fieldType;
	} 

}
