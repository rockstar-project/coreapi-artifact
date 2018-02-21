package com.rockstar.artifact.converter.openapi;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.ServiceDefinition;

@Component
public class OpenApiSchemaToServiceDefinition implements Converter<Schema, ServiceDefinition> {

	@Inject private OpenApiSchemaToEntityDefinition openApiSchemaToEntityDefinition;
	
	public ServiceDefinition convert(Schema schema) {
		ServiceDefinition serviceDefinition = null;
		
		if (schema != null) {
			serviceDefinition = new ServiceDefinition();
			serviceDefinition.setEntity(this.openApiSchemaToEntityDefinition.convert(schema));
		}
		return serviceDefinition;
	}

}
