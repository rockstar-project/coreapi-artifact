package com.rockstar.artifact.converter.openapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Operation;
import com.reprezen.kaizen.oasparser.model3.Parameter;
import com.reprezen.kaizen.oasparser.model3.Path;
import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.ParamDefinition;
import com.rockstar.artifact.codegen.model.SearchDefinition;
import com.rockstar.artifact.codegen.model.SearchParamType;

@Component
public class OpenApiPathToSearchDefinition implements Converter<Map<String, Path>, SearchDefinition> {

	@Inject private OpenApiTypeToAttributeType openApiTypeToAttributeType;
	@Inject private OpenApiSchemaToConstraintDefinition openApiSchemaToConstraintDefintion;
	
	public SearchDefinition convert(Map<String, Path> paths) {
		SearchDefinition searchDefinition = null;
		Collection<ParamDefinition> searchParams = null;
		
		Operation searchOperation = null;
		if (paths != null && !paths.isEmpty()) {
			searchOperation = this.getSearchOperation(paths);
			searchParams = this.getSearchParams(searchOperation);
			searchDefinition = new SearchDefinition();
			searchDefinition.setParams(searchParams);
		}
		return searchDefinition;
	}
	
	private Operation getSearchOperation(Map<String, Path> paths) {
		Path currentPath = null;
		Operation operation = null;
		Iterator<Path> pathIterator = null;
		Operation getOperation = null;

		pathIterator = paths.values().iterator();
		while (pathIterator.hasNext()) {
			currentPath = pathIterator.next();
			getOperation = currentPath.getOperation("get");
			if (getOperation != null) {
				if (getOperation.getOperationId().isEmpty() || getOperation.getOperationId().equalsIgnoreCase("search")) {
					operation = getOperation;;
					break;
				}
			}
		}

		return operation;
	}
	
	private Collection<ParamDefinition> getSearchParams(Operation operation) {
		Collection<ParamDefinition> filters = null;
		Collection<Parameter> params = null;
		ParamDefinition searchfilter = null;
		Schema searchSchema = null;
		
		if (operation != null) {
			params = operation.getParameters();
			if (params != null && !params.isEmpty()) {
				filters = new ArrayList<ParamDefinition>();
				for (Parameter currentParam : params) {
					if (currentParam.getIn().equalsIgnoreCase("query")) {
						searchfilter = new ParamDefinition();
						searchfilter.setName(currentParam.getName());
						searchfilter.setRequired(currentParam.getRequired());
						
						searchSchema = currentParam.getSchema();
						if (searchSchema!= null) {
							searchfilter.setDefaultValue(searchSchema.getDefault());
							searchfilter.setType(this.openApiTypeToAttributeType.convert(searchSchema));
							searchfilter.setConstraints(this.openApiSchemaToConstraintDefintion.convert(searchSchema));
						}
						switch (currentParam.getName()) {
							case "page": 
								searchfilter.setParamType(SearchParamType.PageNumber);
								break;
							case "size": 
								searchfilter.setParamType(SearchParamType.PageSize);
								break;
							case "sort":
								searchfilter.setParamType(SearchParamType.Sort);
								break;
							default:
								searchfilter.setParamType(SearchParamType.Filter);
								break;
						}
						
						filters.add(searchfilter);
					}
				}
			}
		}
		
		return filters;
	}

}
