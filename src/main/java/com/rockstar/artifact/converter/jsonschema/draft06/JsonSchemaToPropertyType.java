package com.rockstar.artifact.converter.jsonschema.draft06;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rockstar.artifact.codegen.definition.PropertyType;

@Component
public class JsonSchemaToPropertyType implements Converter<Map<String, Object>, PropertyType>  {

	@SuppressWarnings("unchecked")
	public PropertyType convert(Map<String, Object> schema) {
		PropertyType schemaPropertyType = null;
		String typeStr = null;
		String refStr = null;
		if (schema != null && !schema.isEmpty()) {
			typeStr = (String) schema.get("type");
			if (StringUtils.isNotEmpty(typeStr)) {
				if (typeStr.equalsIgnoreCase("array") && schema.containsKey("items")) {
					typeStr = (String) ((Map<String, Object>) schema.get("items")).get("type");
					if (StringUtils.isNotEmpty(typeStr)) {
						if (typeStr.equalsIgnoreCase("object")) {
							schemaPropertyType = PropertyType.ObjectArray;
						} else if ( typeStr.equalsIgnoreCase("boolean") || typeStr.equalsIgnoreCase("number") || typeStr.equalsIgnoreCase("string")) {
							schemaPropertyType = PropertyType.PrimitiveArray;
						}
					} 
				} else if (typeStr.equalsIgnoreCase("object") && schema.containsKey("properties")) {
					schemaPropertyType = PropertyType.Object;
				} else if (typeStr.equalsIgnoreCase("boolean") || typeStr.equalsIgnoreCase("number") || typeStr.equalsIgnoreCase("string")) {
					schemaPropertyType = PropertyType.Primitive;
				}
			} else {
				refStr = (String) schema.get("$ref");
				if (StringUtils.isNotEmpty(refStr)) {
					schemaPropertyType = PropertyType.Reference;
				}
			}
			
		}
		return schemaPropertyType;
	}

}
