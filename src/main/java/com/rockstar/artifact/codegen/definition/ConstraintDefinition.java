package com.rockstar.artifact.codegen.definition;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import com.google.common.base.Joiner;

public class ConstraintDefinition {

	private String name;
	private ConstraintType type;
	private Map<String, Object> arguments;

	public ConstraintDefinition() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ConstraintType getType() {
		return this.type;
	}
	
	public void setType(ConstraintType type) {
		this.type = type;
	}
	
	public Map<String, Object> getArguments() {
		if (this.arguments == null) {
			this.arguments = new HashMap<String, Object> ();
		}
		return this.arguments;
	}
	
	public void addArgument(String argumentName, Boolean argumentValue) {
		this.getArguments().put(argumentName, argumentValue);
	}
	
	public void addArgument(String argumentName, Integer argumentValue) {
		this.getArguments().put(argumentName, argumentValue);
	}
	
	public void addArgument(String argumentName, String argumentValue) {
		this.getArguments().put(argumentName, argumentValue);
	}
	
	public String getArgumentString() {
		String argumentStr = null;
		if (this.arguments != null && !this.arguments.isEmpty()) {
			argumentStr = Joiner.on(",").withKeyValueSeparator("=").join(this.arguments);
			if (!StringUtils.isEmpty(argumentStr)) {
				argumentStr = "(" + argumentStr + ")";
			}
		}
		return argumentStr;
	}

	public String toString() {
		StringBuilder constraintsStringBuilder = new StringBuilder();
		String argumentSeparator = null;
		constraintsStringBuilder.append(this.type.name());
		if (this.getArguments() != null && !this.getArguments().isEmpty()) {
			constraintsStringBuilder.append(" [");
			for (Entry<String, Object> argumentEntry : this.getArguments().entrySet()) {
				constraintsStringBuilder.append(argumentSeparator);
				constraintsStringBuilder.append(argumentEntry.getKey());
				constraintsStringBuilder.append("=");
				constraintsStringBuilder.append(argumentEntry.getValue());
				argumentSeparator = ",";
			}
			constraintsStringBuilder.append("]");
		}
		return constraintsStringBuilder.toString();
	}
	
}
