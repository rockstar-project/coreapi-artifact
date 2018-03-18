package com.rockstar.artifact.converter.jsonschema.draft06;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockstar.artifact.codegen.definition.ModelDefinition;
import com.rockstar.artifact.codegen.model.Definition;
import com.rockstar.artifact.codegen.model.MicroserviceDefinition;

@Component
public class JsonSchemaToEventDrivenMicroserviceDefinition implements Converter<URL, MicroserviceDefinition> {

	@Inject private ObjectMapper objectMapper;
	@Inject private JsonSchemaToModelDefinition jsonSchemaToModelDefinition;
	
	public MicroserviceDefinition convert(URL url) {
		MicroserviceDefinition microservice = null;
		TypeReference<HashMap<String, Object>> typeRef = null;
		Map<String, Object> jsonSchemaObject = null;
		ModelDefinition modelDefinition = null;
		String modelName = null;
		
		try {
			typeRef = new TypeReference<HashMap<String, Object>>() {};
			jsonSchemaObject = this.objectMapper.readValue(url, typeRef);
			microservice = new MicroserviceDefinition();
			
			modelDefinition = this.jsonSchemaToModelDefinition.convert(jsonSchemaObject);
			if (StringUtils.isEmpty(modelDefinition.getName())) {
				modelName = StringUtils.substringAfterLast(url.getPath(), File.separator);
				if (StringUtils.contains(modelName, ".")) {
					modelName = StringUtils.substringBefore(modelName, ".");
					modelDefinition.setName(modelName);
				}
			}
			microservice.withDefinition(Definition.Type.Model, modelDefinition);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return microservice;
	}

}