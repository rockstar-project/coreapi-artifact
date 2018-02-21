package com.rockstar.artifact.converter.openapi;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.ConstraintDefinition;
import com.rockstar.artifact.codegen.model.ConstraintType;

@Component
public class OpenApiSchemaToConstraintDefinition implements Converter<Schema, ConstraintDefinition> {

	public ConstraintDefinition convert(Schema schema) {
		ConstraintDefinition constraints = null;
		
		if (schema != null) {
			constraints = new ConstraintDefinition();
			if (schema.getMinimum() != null) {
				Map<String, Object> minArgs = new HashMap<String, Object> ();
				minArgs.put("value", schema.getMinimum());
				constraints.addConstraint(ConstraintType.Min, minArgs);
			}
			if (schema.getMaximum() != null) {
				Map<String, Object> maxArgs = new HashMap<String, Object> ();
				maxArgs.put("value", schema.getMaximum());
				constraints.addConstraint(ConstraintType.Max, maxArgs);
			}
			if (schema.getMinLength() != null || schema.getMaxLength() != null) {
				Map<String, Object> sizeArgs = new HashMap<String, Object> ();
				if (schema.getMinLength() != null) {
					sizeArgs.put("min", schema.getMinLength());
				}
				if (schema.getMaxLength() != null) {
					sizeArgs.put("max", schema.getMaxLength());
				}
				constraints.addConstraint(ConstraintType.Size, sizeArgs);
			}
			if (schema.getPattern() != null) {
				Map<String, Object> patternArgs = new HashMap<String, Object>(); 
				patternArgs.put("regexp", schema.getPattern());
				constraints.addConstraint(ConstraintType.Pattern, patternArgs );
			}
			if (schema.getType().equalsIgnoreCase("string")) {
				if (schema.getFormat() != null) {
					if (schema.getFormat().equalsIgnoreCase("email")) {
						constraints.addConstraint(ConstraintType.Email);
					}
					if (schema.getFormat().equalsIgnoreCase("uriref")) {
						constraints.addConstraint(ConstraintType.URL);
					}
				}
			}
			if (schema.hasEnums()) {
				Map<String, Object> enumArgs = new HashMap<String, Object> ();
				enumArgs.put("allowableValues", schema.getEnums());
				constraints.addConstraint(ConstraintType.ValidEnum, enumArgs);
			}
		}
		return constraints;
	}
}
