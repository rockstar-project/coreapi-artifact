package com.rockstar.artifact.converter.jsonschema.draft06;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockstar.artifact.codegen.definition.ConstraintDefinition;
import com.rockstar.artifact.codegen.definition.ConstraintType;
import com.rockstar.artifact.codegen.definition.ModelDefinition;
import com.rockstar.artifact.codegen.definition.PropertyDefinition;
import com.rockstar.artifact.codegen.definition.PropertyType;

@Component
@SuppressWarnings("unchecked")
public class JsonSchemaToModelDefinition implements Converter<Map<String, Object>, ModelDefinition> {

	@Autowired JsonSchemaToPropertyDefinition jsonSchemaToPropertyDefinition;
	@Autowired JsonSchemaToPropertyType jsonSchemaToPropertyType;
	
	public ModelDefinition convert(Map<String, Object> schema) {
		ModelDefinition modelDefinition = null;
		PropertyDefinition propertyDefinition = null;
		ModelDefinition objectDefinition = null;
		Collection<ModelDefinition> objectDefinitions = null;
		Collection<PropertyDefinition> propertyDefinitions = null;
		Collection<ModelDefinition> collectionDefinitions = null;
		String modelName = null;
		Map<String, Object> schemaProperties = null;
		Collection<String> requiredSchemaProperties = null;
		ConstraintDefinition notEmptyConstraintDefinition = null;
		Map<String, Object> propertyValue = null;
		PropertyType propertyType = null;
		ObjectMapper objectMapper = new ObjectMapper();
		
		if (schema != null) {
			modelDefinition = new ModelDefinition();
			propertyType = this.jsonSchemaToPropertyType.convert(schema);
			switch (propertyType) {
				case ObjectArray:
					schema = (Map<String, Object>) schema.get("items");
					break;
				case Object:
					break;
				default:
					break;
			}
			if (schema != null && schema.containsKey("title")) {
				modelName = (String) schema.get("title");
				modelDefinition.setName(modelName);
			}
			
			schemaProperties = (Map<String, Object>) schema.get("properties");
			requiredSchemaProperties = (Collection<String>) schema.get("required");
			
			if (schemaProperties != null && !schemaProperties.isEmpty()) {
				propertyDefinitions = new ArrayList<PropertyDefinition> ();
				collectionDefinitions = new ArrayList<ModelDefinition> ();
				objectDefinitions = new ArrayList<ModelDefinition> ();
				for (Entry<String, Object> propertyEntry : schemaProperties.entrySet()) {
					propertyValue = 	(Map<String, Object>) propertyEntry.getValue();
					propertyType = this.jsonSchemaToPropertyType.convert(propertyValue);
					
					switch (propertyType) {
						case ObjectArray:
							objectDefinition = this.convert(propertyValue);
							objectDefinition.setName(propertyEntry.getKey());
							collectionDefinitions.add(objectDefinition);
							break;
						case Object:
							objectDefinition = this.convert(propertyValue);
							objectDefinition.setName(propertyEntry.getKey());
							objectDefinitions.add(objectDefinition);
							break;
						case ReferenceArray:
							try {
								TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
								Map<String, Object> jsonSchemaObject = objectMapper.readValue(new URL((String) propertyValue.get("$ref")), typeRef);
								objectDefinition = this.convert(jsonSchemaObject);
								if (objectDefinition != null && StringUtils.isEmpty(objectDefinition.getName())) {
									modelName = StringUtils.substringAfterLast((String) propertyValue.get("$ref"), File.separator);
									objectDefinition.setName(modelName);
								}
								collectionDefinitions.add(objectDefinition);
							} catch (Exception exception) {
								
							}
							break;
						case Reference:
							try {
								
								TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
								
								Map<String, Object> jsonSchemaObject = objectMapper.readValue(new URL((String) propertyValue.get("$ref")), typeRef);
								objectDefinition = this.convert(jsonSchemaObject);
								if (objectDefinition != null && StringUtils.isEmpty(objectDefinition.getName())) {
									modelName = StringUtils.substringAfterLast((String) propertyValue.get("$ref"), File.separator);
									objectDefinition.setName(modelName);
								}
								
								objectDefinitions.add(objectDefinition);
							} catch (Exception exception) {
								
							}
							break;
						case Primitive:
							propertyDefinition = this.jsonSchemaToPropertyDefinition.convert(propertyValue);
							propertyDefinition.setName(propertyEntry.getKey());
							if (requiredSchemaProperties != null && !requiredSchemaProperties.isEmpty()) {
								if (requiredSchemaProperties.contains(propertyEntry.getKey())) {
									notEmptyConstraintDefinition = new ConstraintDefinition();
									notEmptyConstraintDefinition.setType(ConstraintType.NotEmpty);
									propertyDefinition.getConstraints().add(notEmptyConstraintDefinition);
								}
							}
							propertyDefinitions.add(propertyDefinition);
							break;
						default:
							break;
					}
	
				}
				modelDefinition.setProperties(propertyDefinitions);
				modelDefinition.setObjects(objectDefinitions);
				modelDefinition.setCollections(collectionDefinitions);
			}
			
		}
		return modelDefinition;
	}
	
}
