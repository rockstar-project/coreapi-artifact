package com.rockstar.artifact.codegen.model;

public class GeneralDefinition implements Definition {
	
	private String name;
	private String version;
	private String endpointUrl;
	private String ownerEmail;
	
	public GeneralDefinition() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEndpointUrl() {
		return endpointUrl;
	}

	public void setEndpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	
	public String toString() {
		StringBuilder generalStringBuilder = new StringBuilder();
		
		generalStringBuilder.append("\nGeneral Information\n");
		generalStringBuilder.append("name = " + (this.name != null ? this.name : "not specified") + "\n");
		generalStringBuilder.append("version = " + (this.version != null ? this.version : "not specified") + "\n");
		generalStringBuilder.append("contact = " +  (this.ownerEmail != null ? this.ownerEmail : "not specified") + "\n");
		return generalStringBuilder.toString();
	}

}
