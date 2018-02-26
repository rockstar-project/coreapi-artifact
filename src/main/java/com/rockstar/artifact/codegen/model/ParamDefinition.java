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
	
	public boolean getNotEmptyConstraint() {
		return this.hasConstraint(ConstraintType.NotEmpty);
	}
	
	public boolean hasConstraint(ConstraintType constraintType) {
		return this.constraints.hasConstraint(constraintType);
	}

	public String toString() {
		StringBuilder attributeStringBuilder = new StringBuilder();
		
		attributeStringBuilder.append("Parameter Name: " + this.name + "\n");
		attributeStringBuilder.append("Parameter Type: " + this.paramType + "\n");
		attributeStringBuilder.append("Parameter Default Value: " + this.defaultValue + "\n");
		attributeStringBuilder.append(this.constraints);
		
		return attributeStringBuilder.toString();
	}


}
