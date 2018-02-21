package com.rockstar.artifact.codegen.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ServiceDefinition {
	
	private String name;
	private EntityDefinition entity;
	private SearchDefinition search;
	
	public ServiceDefinition() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (this.entity != null) {
			this.entity.setName(name);
		}
	}

	public EntityDefinition getEntity() {
		return entity;
	}

	public void setEntity(EntityDefinition entity) {
		this.entity = entity;
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
