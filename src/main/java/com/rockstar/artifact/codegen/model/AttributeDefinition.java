package com.rockstar.artifact.codegen.model;

import java.util.List;
import java.util.Map;

import com.rockstar.artifact.codegen.model.ConstraintDefinition.Constraint;

public class AttributeDefinition implements Definition {
	
	private String name;
	private AttributeType type;
	private Boolean writeOnly;
	private ConstraintDefinition constraints;
	
	public AttributeDefinition() {
		this.writeOnly = Boolean.FALSE;
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
	
	public Boolean getWriteOnly() {
		return writeOnly;
	}

	public void setWriteOnly(Boolean writeOnly) {
		this.writeOnly = writeOnly;
	}

	public void addConstraint(ConstraintType constraintType) {
		this.constraints.addConstraint(constraintType);
	}

	public void addConstraint(ConstraintType constraintType, Map<String, Object> arguments) {
		this.constraints.addConstraint(constraintType, arguments);
	}
	
	public List<Constraint> getConstraints() {
		List<Constraint> constraints = null;
		if (this.constraints != null) {
			constraints = this.constraints.getConstraints();
		}
		return constraints;
	}

	public boolean hasConstraint(ConstraintType constraintType) {
		return this.constraints.hasConstraint(constraintType);
	}
	
	public boolean isSizeConstraint() {
		return this.hasConstraint(ConstraintType.Size);
	}
	
	public boolean isNotNullConstraint() {
		return this.hasConstraint(ConstraintType.NotNull);
	}
	
	public boolean isNotEmptyConstraint() {
		return this.hasConstraint(ConstraintType.NotEmpty);
	}
	
	public boolean isPatternConstraint() {
		return this.hasConstraint(ConstraintType.Pattern);
	}
	
	public boolean isURLConstraint() {
		return this.hasConstraint(ConstraintType.URL);
	}
	
	public boolean isValidConstraint() {
		return this.hasConstraint(ConstraintType.Valid);
	}
	
	public boolean isValidEnumConstraint() {
		return this.hasConstraint(ConstraintType.ValidEnum);
	}
	
	public Object getSizeMinArgument() {
		Constraint sizeConstraint = this.getConstraint(ConstraintType.Size);
		return sizeConstraint.getArguments().get("min");
	}
	
	public Object getSizeMaxArgument() {
		Constraint sizeConstraint = this.getConstraint(ConstraintType.Size);
		return sizeConstraint.getArguments().get("max");
	}
	
	public Object getValidEnumAllowableValuesArgument() {
		Constraint validEnumConstraint = this.getConstraint(ConstraintType.ValidEnum);
		return validEnumConstraint.getArguments().get("allowableValues");
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
	
	public boolean isType(AttributeType type) {
		return (this.type != null && this.type.equals(type));
	}
	
	public boolean isShortType() {
		return (this.type != null && this.type.equals(AttributeType.Short));
	}
	
	public boolean isIntegerType() {
		return (this.type != null && this.type.equals(AttributeType.Integer));
	}
	
	public boolean isLongType() {
		return (this.type != null && this.type.equals(AttributeType.Long));
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
	
	public boolean isFloatType() {
		return (this.type != null && this.type.equals(AttributeType.Float));
	}
	
	public boolean isDateType() {
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
	
	public boolean isPrimitiveType() {
		return this.isStringType() ||
				this.isDoubleType() ||
				this.isFloatType() ||
				this.isLongType() ||
				this.isShortType() ||
 				this.isIntegerType() ||
				this.isBooleanType() ||
				this.isDateType() ||
				this.isDateTimeType();
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
