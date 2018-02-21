package com.rockstar.artifact.converter.openapi;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.FieldType;

@Component
public class OpenApiTypeToFieldType implements Converter<Schema, FieldType> {

	public FieldType convert(Schema schema) {
		String typeStr = null;
		String formatStr = null;
		FieldType attributeType =  FieldType.String;
		
		typeStr = schema.getType();
		formatStr = schema.getFormat();
		
		if (StringUtils.isNotEmpty(typeStr)) {
			switch (typeStr) {
				case "string":
					if (formatStr != null) {
						switch (formatStr) {
							case "date":
								attributeType = FieldType.LocalDate;
								break;
							case "date-time":
								attributeType = FieldType.DateTime;
								break;
							case "uuid":
								attributeType = FieldType.UUID;
								break;
							case "byte":
							case "binary":
								attributeType = FieldType.Byte;
								break;
							default:
								attributeType = FieldType.String;
								break;
						}
					}
					attributeType = FieldType.String;
					break;
				case "integer":
					switch (formatStr) {
						case "int32":
							attributeType = FieldType.Integer;
							break;
						case "int64":
							attributeType = FieldType.Long;
							break;
						default:
							attributeType = FieldType.Integer;
							break;
					}
				case "number":
					switch (formatStr) {
						case "float":
							attributeType = FieldType.Float;
							break;
						case "double":
							attributeType = FieldType.Double;
							break;
						default:
							attributeType = FieldType.Double;
							break;
					}
					attributeType = FieldType.Double;
					break;
				case "boolean":
					attributeType = FieldType.Boolean;
					break;
				case "object":
					attributeType = FieldType.Object;
					break;
				case "array":
					attributeType = FieldType.Array;
					break;
				default:
					attributeType = FieldType.String;
					break;
			}
		}
		return attributeType;
	} 

}
