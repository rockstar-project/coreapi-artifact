package com.rockstar.artifact.converter.swagger;

import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rockstar.artifact.codegen.model.ParamDefinition;
import com.rockstar.artifact.codegen.model.SearchParamType;

import io.swagger.models.parameters.Parameter;

@Component
public class SwaggerParameterToParamDefinition implements Converter<Parameter, ParamDefinition> {

	public ParamDefinition convert(Parameter swaggerParameter) {
		ParamDefinition paramDefinition = null;
		Map<String, Object> extensions = null;
		
		if (swaggerParameter != null) {
			paramDefinition = new ParamDefinition();
			paramDefinition.setName(swaggerParameter.getName());
			
			extensions = swaggerParameter.getVendorExtensions();
			if (extensions != null && !extensions.isEmpty()) {
				extensions.get("x-PageSize");
				if (!StringUtils.isEmpty(extensions.get("x-PageSize"))) {
					paramDefinition.setParamType(SearchParamType.PageSize);
				}
				else if (!StringUtils.isEmpty(extensions.get("x-PageNumber"))) {
					paramDefinition.setParamType(SearchParamType.PageNumber);
				}
				else if (!StringUtils.isEmpty(extensions.get("x-SearchTerm"))) {
					paramDefinition.setParamType(SearchParamType.SearchTerm);
				} else {
					paramDefinition.setParamType(SearchParamType.Filter);
				}
			}
		}
		return paramDefinition;
	}

}
