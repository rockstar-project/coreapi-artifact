package com.rockstar.artifact.converter.swagger;

import java.util.Map.Entry;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.SampleDataDefinition;

@Component
public class SwaggerDefinitionToSampleDataDefinition implements Converter<Schema, SampleDataDefinition>{

	public SampleDataDefinition convert(Schema schema) {
		SampleDataDefinition dataDefinition = null;
		Schema propertySchema = null;
		Object sampleData = null;
		String propertyName = null;
		
		if (schema != null) {
			dataDefinition = new SampleDataDefinition();
			
			if (schema.getProperties() != null && !schema.getProperties().isEmpty()) {
				for (Entry<String, Schema> schemaEntry : schema.getProperties().entrySet()) {
					propertyName = schemaEntry.getKey();
					propertySchema = schemaEntry.getValue();
					if (propertySchema != null) {
						sampleData = propertySchema.getExample();
						dataDefinition.setData(propertyName, sampleData);
					}
				}
				
			}
		}
		return dataDefinition;
	}

}
