package com.rockstar.artifact.converter.openapi;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.reprezen.kaizen.oasparser.model3.Operation;
import com.reprezen.kaizen.oasparser.model3.Parameter;
import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.ParamDefinition;
import com.rockstar.artifact.codegen.model.SearchDefinition;
import com.rockstar.artifact.codegen.model.SearchParamType;

@Component
public class OpenApiPathToSearchDefinition implements Converter<Operation, SearchDefinition> {

	@Inject private OpenApiTypeToAttributeType openApiTypeToAttributeType;
	@Inject private OpenApiSchemaToConstraintDefinition openApiSchemaToConstraintDefintion;
	
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
		ParamDefinition searchParameter = null;
		Schema searchSchema = null;
		
		if (operation != null) {
			params = operation.getParameters();
			if (params != null && !params.isEmpty()) {
				searchParameters = new ArrayList<ParamDefinition>();
				for (Parameter currentParam : params) {
					if (currentParam.getIn().equalsIgnoreCase("query")) {
						searchParameter = new ParamDefinition();
						searchParameter.setName(currentParam.getName());
						searchParameter.setRequired(currentParam.getRequired());
						
						searchSchema = currentParam.getSchema();
						if (searchSchema!= null) {
							searchParameter.setDefaultValue(searchSchema.getDefault());
							searchParameter.setType(this.openApiTypeToAttributeType.convert(searchSchema));
							searchParameter.setConstraints(this.openApiSchemaToConstraintDefintion.convert(searchSchema));
						}
						
						if (!StringUtils.isEmpty(currentParam.getExtension("x-PageSize"))) {
							searchParameter.setParamType(SearchParamType.PageSize);
						}
						else if (!StringUtils.isEmpty(currentParam.getExtension("x-PageNumber"))) {
							searchParameter.setParamType(SearchParamType.PageNumber);
						}
						else if (!StringUtils.isEmpty(currentParam.getExtension("x-SearchTerm"))) {
							searchParameter.setParamType(SearchParamType.SearchTerm);
						} else {
							searchParameter.setParamType(SearchParamType.Filter);
						}
						
						
						searchParameters.add(searchParameter);
					}
				}
			}
		}
		
		return searchParameters;
	}

}
