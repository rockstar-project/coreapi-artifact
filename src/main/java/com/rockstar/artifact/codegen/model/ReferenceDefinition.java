package com.rockstar.artifact.codegen.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ReferenceDefinition {
	
	private String type;
	private String target;
	
	public ReferenceDefinition(String type, String target) {
		this.type = type;
		this.target = target;
	}

	public String getType() {
		return type;
	}

	public String getTarget() {
		return target;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
