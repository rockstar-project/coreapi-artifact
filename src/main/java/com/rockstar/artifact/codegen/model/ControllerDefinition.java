package com.rockstar.artifact.codegen.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ControllerDefinition {
	
	private ResourceDefinition resource;
	private ServiceDefinition service;
	private SearchDefinition search;
	
	public ControllerDefinition() {
	}
	
	public ResourceDefinition getResource() {
		return resource;
	}

	public void setResource(ResourceDefinition resource) {
		this.resource = resource;
	}

	public ServiceDefinition getService() {
		return service;
	}

	public void setService(ServiceDefinition service) {
		this.service = service;
	}

	public SearchDefinition getSearch() {
		return search;
	}

	public void setSearch(SearchDefinition search) {
		this.search = search;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
 