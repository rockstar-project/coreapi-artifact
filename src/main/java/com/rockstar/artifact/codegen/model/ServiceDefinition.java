package com.rockstar.artifact.codegen.model;

public class ServiceDefinition implements Definition {
	
	private String name;
	private EntityDefinition entity;
	private SearchDefinition search;
	private MessagingDefinition messaging;
	
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

	public SearchDefinition getSearch() {
		return search;
	}

	public void setSearch(SearchDefinition search) {
		this.search = search;
	}

	public MessagingDefinition getMessaging() {
		return messaging;
	}

	public void setMessaging(MessagingDefinition messaging) {
		this.messaging = messaging;
	}

}
