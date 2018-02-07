package com.rockstar.artifact.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

public class Specification {
	
	private String location;
	private String type;
	private String version;
	
	public Specification() {
	}

	@NotEmpty
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@NotEmpty
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
