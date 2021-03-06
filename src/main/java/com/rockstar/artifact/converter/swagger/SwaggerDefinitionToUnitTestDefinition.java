package com.rockstar.artifact.converter.swagger;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.SampleDataDefinition;
import com.rockstar.artifact.codegen.model.UnitTestDefinition;

@Component
public class SwaggerDefinitionToUnitTestDefinition implements Converter<Schema, UnitTestDefinition> {

	@Inject private SwaggerDefinitionToSampleDataDefinition openApiSchemaToSampleData;
	
	public UnitTestDefinition convert(Schema schema) {
		UnitTestDefinition testDefinition = null;
		SampleDataDefinition sampleData = null;
		
		if (schema != null) {
			testDefinition = new UnitTestDefinition();
			sampleData = this.openApiSchemaToSampleData.convert(schema);
			testDefinition.setData(sampleData);
		}
		return testDefinition;
	}

}
