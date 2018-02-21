package com.rockstar.artifact.codegen.model;

public class AttributeDefinition {
	
	private String name;
	private AttributeType type;
	private ConstraintDefinition constraints;
	
	public AttributeDefinition() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AttributeType getType() {
		return type;
	}

	public void setType(AttributeType type) {
		this.type = type;
	}

	public ConstraintDefinition getConstraints() {
		return constraints;
	}

	public boolean hasConstraint(ConstraintType constraintType) {
		return this.constraints.hasConstraint(constraintType);
	}
	
	public boolean hasSizeConstraint() {
		return this.hasConstraint(ConstraintType.Size);
	}
	
	public boolean hasNotNullConstraint() {
		return this.hasConstraint(ConstraintType.NotNull);
	}
	
	public boolean hasNotBlankConstraint() {
		return this.hasConstraint(ConstraintType.NotEmpty);
	}
	
	public ConstraintDefinition.Constraint getConstraint(ConstraintType constraintType) {
		return constraints.getConstraint(constraintType);
	}
	
	public ConstraintDefinition.Constraint getSizeConstraint() {
		return (this.getConstraint(ConstraintType.Size));
	}
	
	public ConstraintDefinition.Constraint getNotNullConstraint() {
		return (this.getConstraint(ConstraintType.NotNull));
	}
	
	public ConstraintDefinition.Constraint getNotBlankConstraint() {
		return (this.getConstraint(ConstraintType.NotEmpty));
	}
	
	public ConstraintDefinition.Constraint getPatternConstraint() {
		return (this.getConstraint(ConstraintType.Pattern));
	}
	
	public ConstraintDefinition.Constraint getURLConstraint() {
		return (this.getConstraint(ConstraintType.URL));
	}

	public void setConstraints(ConstraintDefinition constraints) {
		this.constraints = constraints;
	}
	
	public boolean isIntegerType() {
		return (this.type != null && this.type.equals(AttributeType.Integer));
	}
	
	public boolean isStringType() {
		return (this.type != null && this.type.equals(AttributeType.String));
	}
	
	public boolean isBooleanType() {
		return (this.type != null && this.type.equals(AttributeType.Boolean));
	}
	
	public boolean isDoubleType() {
		return (this.type != null && this.type.equals(AttributeType.Double));
	}
	
	public boolean isLocalDateType() {
		return (this.type != null && this.type.equals(AttributeType.LocalDate));
	}
	
	public boolean isDateTimeType() {
		return (this.type != null && this.type.equals(AttributeType.DateTime));
	}
	
	public boolean isArrayType() {
		return (this.type != null && this.type.equals(AttributeType.Array));
	}
	
	public boolean isObjectType() {
		return (this.type != null && this.type.equals(AttributeType.Object));
	}
	
	public String toString() {
		StringBuilder attributeStringBuilder = new StringBuilder();
		
		attributeStringBuilder.append(this.name);
		attributeStringBuilder.append(" (");
		attributeStringBuilder.append(this.type);
		attributeStringBuilder.append(")");
		attributeStringBuilder.append("\n");
		attributeStringBuilder.append(this.constraints);
		
		return attributeStringBuilder.toString();
	}

}
