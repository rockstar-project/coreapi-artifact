package com.rockstar.artifact.codegen.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ArtifactDefinition {
	
	private GeneralDefinition general;
	private WebDefinition web;
	private SecurityDefinition security;
	
	public ArtifactDefinition() {
	}
	
	public GeneralDefinition getGeneral() {
		return general;
	}

	public void setGeneral(GeneralDefinition general) {
		this.general = general;
	}
	
	public WebDefinition getWeb() {
		return web;
	}

	public void setWeb(WebDefinition web) {
		this.web = web;
	}

	public SecurityDefinition getSecurity() {
		return security;
	}

	public void setSecurity(SecurityDefinition security) {
		this.security = security;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
