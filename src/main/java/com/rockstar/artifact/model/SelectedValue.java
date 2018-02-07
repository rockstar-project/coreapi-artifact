package com.rockstar.artifact.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

public class SelectedValue {
	
	private String value;
	private String version;
	
	public SelectedValue() {
	}

	@NotEmpty
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@NotEmpty
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
