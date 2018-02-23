package com.rockstar.artifact.converter.openapi;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.RepositoryDefinition;

@Component
public class OpenApiSchemaToRepositoryDefinition implements Converter<Schema, RepositoryDefinition> {
	
	public RepositoryDefinition convert(Schema schema) {
		RepositoryDefinition repositoryDefinition = null;
		
		if (schema != null) {
			repositoryDefinition = new RepositoryDefinition();
		}
		return repositoryDefinition;
		
	}

}
