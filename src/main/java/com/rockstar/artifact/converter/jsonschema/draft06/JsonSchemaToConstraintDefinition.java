package com.rockstar.artifact.converter.jsonschema.draft06;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rockstar.artifact.codegen.definition.ConstraintDefinition;

@Component
public class JsonSchemaToConstraintDefinition implements Converter<Map<String, Object>, Collection<ConstraintDefinition>>  {

	public Collection<ConstraintDefinition> convert(Map<String, Object> schema) {
		Collection<ConstraintDefinition> constraintDefinitions = null;
		
		if (schema != null) {
			constraintDefinitions = new ArrayList<ConstraintDefinition> ();
		}
		return constraintDefinitions;
	}

}
