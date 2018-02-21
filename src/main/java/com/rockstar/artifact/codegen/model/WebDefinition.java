package com.rockstar.artifact.codegen.model;

import java.util.Collection;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class WebDefinition {
	
	private Collection<ControllerDefinition> controllers;
	
	public WebDefinition() {
	}

	public Collection<ControllerDefinition> getControllers() {
		return controllers;
	}

	public void setControllers(Collection<ControllerDefinition> controllers) {
		this.controllers = controllers;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
