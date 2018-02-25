package com.rockstar.artifact.codegen.model;

public class FieldDefinition {
	
	private String name;
	private FieldType type;
	private Boolean unique;
	private Boolean enumeration;
	private Boolean readOnly;
	
	public FieldDefinition() {
		this.readOnly = Boolean.FALSE;
		this.unique = Boolean.FALSE;
		this.enumeration = Boolean.FALSE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Boolean getUnique() {
		return unique;
	}

	public void setUnique(Boolean unique) {
		this.unique = unique;
	}
	
	public Boolean getEnumeration() {
		return enumeration;
	}

	public void setEnumeration(Boolean enumeration) {
		this.enumeration = enumeration;
	}
	
	public boolean isType(FieldType type) {
		return (this.type != null && this.type.equals(type));
	}
	
	public boolean isStringType() {
		return (this.type != null && this.type.equals(FieldType.String));
	}
	
	public boolean isBooleanType() {
		return (this.type != null && this.type.equals(FieldType.Boolean));
	}
	
	public boolean isIntegerType() {
		return (this.type != null && this.type.equals(FieldType.Integer));
	}
	
	public boolean isShortType() {
		return (this.type != null && this.type.equals(FieldType.Short));
	}
	
	public boolean isLongType() {
		return (this.type != null && this.type.equals(FieldType.Long));
	}
	
	public boolean isDoubleType() {
		return (this.type != null && this.type.equals(FieldType.Double));
	}
	
	public boolean isFloatType() {
		return (this.type != null && this.type.equals(FieldType.Float));
	}
	
	public boolean isDateType() {
		return (this.type != null && this.type.equals(FieldType.LocalDate));
	}
	
	public boolean isDateTimeType() {
		return (this.type != null && this.type.equals(FieldType.DateTime));
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
	
	public boolean isArrayType() {
		return (this.type != null && this.type.equals(FieldType.Array));
	}
	
	public boolean isObjectType() {
		return (this.type != null && this.type.equals(FieldType.Object));
	}
	

	public String toString() {
		StringBuilder fieldStringBuilder = new StringBuilder();
		
		fieldStringBuilder.append(this.name);
		fieldStringBuilder.append(" (");
		fieldStringBuilder.append(this.type);
		fieldStringBuilder.append(")");
		fieldStringBuilder.append(" - ");
		fieldStringBuilder.append("unique = " + (this.unique != null && this.unique ? "Yes" : "No"));
		fieldStringBuilder.append(",");
		fieldStringBuilder.append("enum = " + (this.enumeration != null && this.enumeration ? "Yes" : "No"));
		fieldStringBuilder.append(",");
		fieldStringBuilder.append("readOnly = " + (this.readOnly != null && this.readOnly ? "Yes" : "No"));
		fieldStringBuilder.append("\n");
		return fieldStringBuilder.toString();
	}
	
}
