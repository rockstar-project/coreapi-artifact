package com.rockstar.artifact.converter.swagger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rockstar.artifact.codegen.model.MySqlType;

import io.swagger.models.properties.Property;

@Component
public class SwaggerPropertyTypeToMySqlType implements Converter<Property, MySqlType> {

	public MySqlType convert(Property schema) {
		MySqlType mysqlType = null;
		String typeStr = null;
		String formatStr = null;
		
		if (schema != null) {
			typeStr = schema.getType();
			formatStr = schema.getFormat();
			if (StringUtils.isNotEmpty(typeStr)) {
				switch (typeStr) {
					case "string":
						if (formatStr != null) {
							switch (formatStr) {
								case "date":
									mysqlType = MySqlType.Date;
									break;
								case "date-time":
									mysqlType = MySqlType.DateTime;
									break;
								case "uuid":
									mysqlType = MySqlType.UUID;
									break;
								case "byte":
								case "binary":
									mysqlType = MySqlType.Blob;
									break;
								default:
									mysqlType = MySqlType.String;
									break;
							}
						} else {
							mysqlType = MySqlType.String;
						}
						break;
					case "integer":
						if (formatStr != null) {
							switch (formatStr) {
								case "int32":
									mysqlType = MySqlType.Integer;
									break;
								case "int64":
									mysqlType = MySqlType.BigInteger;
									break;
								default:
									mysqlType = MySqlType.Integer;
									break;
							}
						} else {
							mysqlType = MySqlType.Integer;
						}
						break;
					case "number":
						if (formatStr != null) {
							switch (formatStr) {
								case "float":
								case "double":
									mysqlType = MySqlType.Decimal;
									break;
								default:
									mysqlType = MySqlType.Decimal;
									break;
							}
						} else {
							mysqlType = MySqlType.Decimal;
						}
						break;
					case "boolean":
						mysqlType = MySqlType.Boolean;
						break;
					case "object":
						mysqlType = MySqlType.ParentId;
						break;
					case "array":
						mysqlType = MySqlType.ForeignKey;
						break;
					default:
						mysqlType = MySqlType.String;
						break;
				}
			}
		}
		return mysqlType;
	}

}
