package com.rockstar.artifact.codegen.model;

public class FieldDefinition {
	
	private String name;
	private FieldType type;
	private Boolean unique;
	private Boolean readOnly;
	
	public FieldDefinition() {
		this.readOnly = Boolean.FALSE;
		this.unique = Boolean.FALSE;
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
	
}
