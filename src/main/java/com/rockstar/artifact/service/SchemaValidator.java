package com.rockstar.artifact.service;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import com.reprezen.kaizen.oasparser.OpenApi3Parser;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.rockstar.artifact.annotation.ValidSchema;
import com.rockstar.artifact.model.Specification;

public class SchemaValidator implements ConstraintValidator<ValidSchema, Specification>  {

	@Override
	public void initialize(ValidSchema spec) {
		
	}

	public boolean isValid(Specification specification, ConstraintValidatorContext validatorContext) {
		boolean isValid = true;
		
		String specificationLocation = null;
		String specificationVersion = null;
		String specificationType = null;
		String specificationContent = null;

		URI specURI = null;
		
		if (specification != null) {
			specificationLocation = specification.getLocation();
			specificationVersion = specification.getVersion();
			specificationType = specification.getType();
			specificationContent = specification.getContent();
			
			try {
				specURI = new URI(specificationLocation);
			} catch (URISyntaxException ex) {
				// this should never happen
			}
			switch (specificationType) {
				case "openapi":
					switch (specificationVersion) {
						case "3":
							OpenApi3 openapi3Model = null;
							
							if (!StringUtils.isEmpty(specificationLocation)) {
								openapi3Model = new OpenApi3Parser().parse(specURI, true);
							} else if (!StringUtils.isEmpty(specificationContent)) {
								//openapi3Model = new OpenApi3Parser();
							}
							
							if (openapi3Model != null) {
								if (!openapi3Model.isValid()) {
									if (!openapi3Model.getValidationItems().isEmpty()) {
										isValid = false;
									}
								}
							}
							break;
						
					}
					break;
				case "swagger":
					switch (specificationVersion) {
						case "2":
							break;
					}
					break;
				case "raml":
					switch (specificationVersion) {
						case "1":
							if (!StringUtils.isEmpty(specificationLocation)) {
								
							} else if (!StringUtils.isEmpty(specificationContent)) {
								
							}
							break;
						default:
					}
					break;
				case "jsonschema":
					switch (specificationVersion) {
						case "draft-4":
							break;
						case "draft-6":
							break;
						default:
					}
					break;
				default:
				}
		}
		return isValid;
		
	}
}
