package com.rockstar.artifact.converter.swagger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;
import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.AttributeType;
import com.rockstar.artifact.codegen.model.ConstraintDefinition;
import com.rockstar.artifact.codegen.model.ConstraintType;

import io.swagger.models.properties.Property;

@Component
public class SwaggerPropertyToConstraintDefinition implements Converter<Property, ConstraintDefinition> {

	@Inject SwaggerPropertyTypeToAttributeType swaggerPropertyTypeToAttributeType;
	
	public ConstraintDefinition convert(Property schema) {
		ConstraintDefinition constraints = null;
		
		if (schema != null) {
			constraints = new ConstraintDefinition();
			
			if (schema.getType() != null) {
				
				if (schema.getType().equalsIgnoreCase("array")) {
					// TODO: check to see if openapi can have a constraint on empty list
				} else if (schema.getType().equalsIgnoreCase("object")) {
					constraints.addConstraint(ConstraintType.Valid);
				} else {
					/*
					if (schema.getMinimum() != null) {
						Map<String, Object> minArgs = new HashMap<String, Object> ();
						Number minimumValue = schema.get();
						AttributeType type = this.swaggerPropertyTypeToAttributeType.convert(schema);
					
						if (type != null) {
							switch (type) {
								case Integer:
									minArgs.put("value", minimumValue.intValue());
									break;
								case Long:
									minArgs.put("value", minimumValue.longValue());
									break;
								case Double:
									minArgs.put("value", new BigDecimal(minimumValue.doubleValue()));
									break;
								case Float:
									minArgs.put("value", new BigDecimal(minimumValue.floatValue()));
									break;
								case Short:
									minArgs.put("value", minimumValue.shortValue());
									break;
								case Byte:
									minArgs.put("value", minimumValue.byteValue());
									break;
								default:
									break;
							}
						}
						
						constraints.addConstraint(ConstraintType.Min, minArgs);
					}
					
					if (schema.getMaximum() != null) {
						Map<String, Object> maxArgs = new HashMap<String, Object> ();
						Number maximumValue = schema.getMaximum();
						AttributeType type = this.openApiTypeToAttributeType.convert(schema);
						
						if (type != null) {
							switch (type) {
								case Integer:
									maxArgs.put("value", maximumValue.intValue());
									break;
								case Long:
									maxArgs.put("value", maximumValue.longValue());
									break;
								case Double:
									maxArgs.put("value", new BigDecimal(maximumValue.doubleValue()));
									break;
								case Float:
									maxArgs.put("value", new BigDecimal(maximumValue.floatValue()));
									break;
								case Short:
									maxArgs.put("value", maximumValue.shortValue());
									break;
								case Byte:
									maxArgs.put("value", maximumValue.byteValue());
									break;
								default:
									break;
							}
						}
						
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
						patternArgs.put("regexp", "\"" + schema.getPattern() + "\"");
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
						enumArgs.put("ignoreCase", true);
						enumArgs.put("allowableValues", "{\"" + Joiner.on("\",\"").join(schema.getEnums()) + "\"}");
						constraints.addConstraint(ConstraintType.ValidEnum, enumArgs);
					}
					*/
				}
					
			}
		}
		
		return constraints;
	}
}
