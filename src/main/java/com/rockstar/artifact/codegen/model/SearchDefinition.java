package com.rockstar.artifact.codegen.model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SearchDefinition implements Definition {
	
	private String name;
	private Collection<ParamDefinition> params;
	
	public SearchDefinition() {
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Collection<ParamDefinition> getParams() {
		return params;
	}

	public void setParams(Collection<ParamDefinition> params) {
		this.params = params;
	}

	public Collection<ParamDefinition> getFilters() {
		return this.getSearchParams(SearchParamType.Filter);
	}
	
	public ParamDefinition getQueryParam() {
		return this.getSearchParam(SearchParamType.SearchTerm);
	}
	
	public ParamDefinition getPageSizeParam() {
		return this.getSearchParam(SearchParamType.PageSize);
	}
	
	public ParamDefinition getPageNumberParam() {
		return this.getSearchParam(SearchParamType.PageNumber);
	}
	
	public Collection<ParamDefinition> getSortParams() {
		return this.getSearchParams(SearchParamType.Sort);
	}
	
	public ParamDefinition getSearchParam(SearchParamType paramType) {
		ParamDefinition searchParam = null;
		Collection<ParamDefinition> params = null;
		Iterator<ParamDefinition> iterator = null;
		
		params = this.getSearchParams(SearchParamType.SearchTerm);
		
		if (params != null) {
			iterator = params.iterator();
			if (iterator != null && iterator.hasNext()) {
				searchParam = iterator.next();
			}
		}
		return searchParam;
	}
	
	public Collection<ParamDefinition> getSearchParams(SearchParamType paramType) {
		Collection<ParamDefinition> filters = null;
		
		if (this.params != null && !this.params.isEmpty()) {
			filters = new ArrayList<ParamDefinition> ();
			for (ParamDefinition currentParam : this.params) {
				
				if (currentParam.getParamType().equals(paramType)) {
					filters.add(currentParam);
				}
			}
		}
		return filters;
	}
	
	public String toString() {
		StringBuilder searchStringBuilder = new StringBuilder();
		
		searchStringBuilder.append("\nSearch Name: " + this.name + "\n");
		searchStringBuilder.append("Parameters\n");

		if (this.params != null && !this.params.isEmpty()) {
			
			for (ParamDefinition current: this.params) {
				searchStringBuilder.append(current);
				searchStringBuilder.append("\n");
			}
		} else {
			searchStringBuilder.append(" - 0 found");
		}
		
		searchStringBuilder.append("\n");
		
		return searchStringBuilder.toString();
	}

}
