package com.rockstar.artifact.converter.swagger;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.RestApiTestDefinition;
import com.rockstar.artifact.codegen.model.SampleDataDefinition;

@Component
public class SwaggerDefinitionToRestApiTestDefinition implements Converter<Schema, RestApiTestDefinition> {

	@Inject private SwaggerDefinitionToSampleDataDefinition openApiSchemaToSampleData;
	
	public RestApiTestDefinition convert(Schema schema) {
		RestApiTestDefinition testDefinition = null;
		SampleDataDefinition sampleData = null;
		
		if (schema != null) {
			testDefinition = new RestApiTestDefinition();
			sampleData = this.openApiSchemaToSampleData.convert(schema);
			testDefinition.setData(sampleData);
		}
		return testDefinition;
	}

}
