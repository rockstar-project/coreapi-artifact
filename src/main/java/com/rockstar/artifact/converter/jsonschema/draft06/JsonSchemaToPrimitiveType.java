package com.rockstar.artifact.converter.jsonschema.draft06;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rockstar.artifact.codegen.definition.PrimitiveType;

@Component
public class JsonSchemaToPrimitiveType implements Converter<Map<String, Object>, PrimitiveType> {

	public PrimitiveType convert(Map<String, Object> schema) {
		PrimitiveType propertyType = null;
		String typeStr = null;
		String formatStr = null;
		
		if (schema != null) {
			typeStr = (String) schema.get("type");
			formatStr = (String) schema.get("format");
			
			if (StringUtils.isNotEmpty(typeStr)) {
				switch (typeStr) {
					case "string":
						if (formatStr != null) {
							switch (formatStr) {
								case "date":
									propertyType = PrimitiveType.LocalDate;
									break;
								case "time":
									propertyType = PrimitiveType.Time;
									break;
								case "date-time":
									propertyType = PrimitiveType.DateTime;
									break;
								case "uuid":
									propertyType = PrimitiveType.UUID;
									break;
								case "byte":
								case "binary":
									propertyType = PrimitiveType.Byte;
									break;
								case "uri":
								case "phone":
								case "email":
									propertyType = PrimitiveType.String;
									break;
								default:
									propertyType = PrimitiveType.String;
									break;
							}
						} else {
							propertyType = PrimitiveType.String;
						}
						break;
					case "integer":
						if (formatStr != null) {
							switch (formatStr) {
								case "int32":
									propertyType = PrimitiveType.Integer;
									break;
								case "int64":
									propertyType = PrimitiveType.Long;
									break;
								default:
									propertyType = PrimitiveType.Integer;
									break;
							}
						} else {
							propertyType = PrimitiveType.Integer;
						}
						break;
					case "number":
						if (formatStr != null) {
							switch (formatStr) {
								case "float":
									propertyType = PrimitiveType.Float;
									break;
								case "double":
									propertyType = PrimitiveType.Double;
									break;
								default:
									propertyType = PrimitiveType.Double;
									break;
							}
						} else {
							propertyType = PrimitiveType.Double;
						}
						break;
					case "boolean":
						propertyType = PrimitiveType.Boolean;
						break;
					default:
						propertyType = PrimitiveType.String;
						break;
				}
			}
		}
		return propertyType;
	}

}
