package com.rockstar.artifact.service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.rockstar.artifact.annotation.SupportSpec;
import com.rockstar.artifact.model.Specification;

public class SupportedSpecValidator implements ConstraintValidator<SupportSpec, Specification> {

	public void initialize(SupportSpec type) {
		
	}

	public boolean isValid(Specification specification, ConstraintValidatorContext validatorContext) {
		boolean isValid = true;
		String specificationVersion = null;
		String specificationType = null;
		
		if (specification != null) {
			specificationVersion = specification.getVersion();
			specificationType = specification.getType();
			
			switch (specificationType) {
				case "openapi":
					switch (specificationVersion) {
						case "3":
							break;
						default:
							isValid = false;
					}
					break;
				case "swagger":
					switch (specificationVersion) {
						case "2":
							break;
						default:
							isValid = false;
					}
					break;
				case "raml":
					switch (specificationVersion) {
						case "1":
							break;
						default:
							isValid = false;
					}
					break;
				case "jsonschema":
					switch (specificationVersion) {
						case "draft-4":
							break;
						case "draft-6":
							break;
						case "draft-7":
							break;
						default:
							isValid = false;
					}
					break;
				default:
					isValid = false;
			}
		}
		
		return isValid;
		
	}

}
