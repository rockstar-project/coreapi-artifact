package com.rockstar.artifact.codegen.model;

public class ServiceDefinition implements Definition {
	
	private String name;
	private EntityDefinition entity;
	private RepositoryDefinition repository;
	private SearchDefinition search;
	
	public ServiceDefinition() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EntityDefinition getEntity() {
		return entity;
	}

	public void setEntity(EntityDefinition entity) {
		this.entity = entity;
	}

	public RepositoryDefinition getRepository() {
		return repository;
	}

	public void setRepository(RepositoryDefinition repository) {
		this.repository = repository;
	}

	public SearchDefinition getSearch() {
		return search;
	}

	public void setSearch(SearchDefinition search) {
		this.search = search;
	}

}
