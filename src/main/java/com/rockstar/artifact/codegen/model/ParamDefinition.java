package com.rockstar.artifact.codegen.model;

public class ParamDefinition {
	
	private String name;
	private Boolean required;
	private AttributeType type;
	private Object defaultValue;
	private SearchParamType paramType;
	private ConstraintDefinition constraints;
	
	public ParamDefinition() {
		this.type = AttributeType.String;
		this.required = Boolean.FALSE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public SearchParamType getParamType() {
		return paramType;
	}

	public void setParamType(SearchParamType paramType) {
		this.paramType = paramType;
	}

	public AttributeType getType() {
		return type;
	}

	public void setType(AttributeType type) {
		this.type = type;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public ConstraintDefinition getConstraints() {
		return constraints;
	}

	public void setConstraints(ConstraintDefinition constraints) {
		this.constraints = constraints;
	}

	public String toString() {
		StringBuilder attributeStringBuilder = new StringBuilder();
		
		attributeStringBuilder.append(this.name);
		attributeStringBuilder.append(" (");
		attributeStringBuilder.append(this.type);
		attributeStringBuilder.append(")");
		attributeStringBuilder.append("\n");
		
		return attributeStringBuilder.toString();
	}


}
