package com.rockstar.artifact.service;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;

import com.reprezen.kaizen.oasparser.OpenApi3Parser;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.rockstar.artifact.annotation.ValidSpec;
import com.rockstar.artifact.model.Specification;

public class SchemaSpecValidator  implements ConstraintValidator<ValidSpec, Specification>  {

	@Override
	public void initialize(ValidSpec spec) {
		
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
								RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi(specificationContent);
								if (ramlModelResult.hasErrors()) {
									isValid = false;
								}
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
