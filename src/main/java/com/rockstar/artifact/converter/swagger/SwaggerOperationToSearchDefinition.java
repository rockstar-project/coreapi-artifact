package com.rockstar.artifact.converter.swagger;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rockstar.artifact.codegen.model.ParamDefinition;
import com.rockstar.artifact.codegen.model.SearchDefinition;

import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;

@Component
public class SwaggerOperationToSearchDefinition implements Converter<Operation, SearchDefinition> {

	@Inject private SwaggerParameterToParamDefinition swaggerParameterToParamDefinition;
	
	public SearchDefinition convert(Operation searchOperation) {
		SearchDefinition searchDefinition = null;
		Collection<ParamDefinition> searchParams = null;
		
		if (searchOperation != null) {
			searchParams = this.getSearchParams(searchOperation);
			searchDefinition = new SearchDefinition();
			searchDefinition.setParams(searchParams);
		}
		return searchDefinition;
	}
	
	private Collection<ParamDefinition> getSearchParams(Operation operation) {
		Collection<ParamDefinition> searchParameters = null;
		Collection<Parameter> params = null;
		
		if (operation != null) {
			params = operation.getParameters();
			if (params != null && !params.isEmpty()) {
				searchParameters = new ArrayList<ParamDefinition>();
				for (Parameter currentParam : params) {
					if (currentParam.getIn().equalsIgnoreCase("query")) {
						searchParameters.add(this.swaggerParameterToParamDefinition.convert(currentParam));
					}
				}
			}
		}
		
		return searchParameters;
	}

}
