package com.rockstar.artifact.codegen.model;

public class ControllerDefinition implements Definition {
	
	private String name;
	private Definition resource;
	private Definition service;
	private Definition search;
	private Definition entity;
	
	public ControllerDefinition() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Definition getResource() {
		return resource;
	}

	public void setResource(Definition resource) {
		this.resource = resource;
	}

	public Definition getService() {
		return service;
	}

	public void setService(Definition service) {
		this.service = service;
	}

	public Definition getSearch() {
		return search;
	}

	public void setSearch(Definition search) {
		this.search = search;
	}

	public Definition getEntity() {
		return entity;
	}

	public void setEntity(Definition entity) {
		this.entity = entity;
	}

	public String toString() {
		StringBuilder controllerStringBuilder = new StringBuilder();
		
		controllerStringBuilder.append("Controller name = " + (this.name != null ? this.name : "not specified") + "\n");
		if (this.resource != null) {
			controllerStringBuilder.append(resource);
		}
		if (this.service != null) {
			controllerStringBuilder.append(service);
		}
		if (this.search != null) {
			controllerStringBuilder.append(search);
		}
		if (this.entity != null) {
			controllerStringBuilder.append(entity);
		}
		return controllerStringBuilder.toString();
	}
}
 