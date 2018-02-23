package com.rockstar.artifact.controller;

import java.util.Arrays;

public class JsonSchemaDefinition {
	
	private String type;
	private String description;
	private String example;
	private Integer maxLength;
	private String[] enumeration;
	private Boolean isDefault;
	
	public JsonSchemaDefinition() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String[] getEnumeration() {
		return enumeration;
	}

	public void setEnumeration(String[] enumeration) {
		this.enumeration = enumeration;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public String toString() {
		return "JsonSchemaDefinition [type=" + type + ", description=" + description + ", example=" + example
				+ ", maxLength=" + maxLength + ", enumeration=" + Arrays.toString(enumeration) + ", isDefault="
				+ isDefault + "]";
	}
	
	

}
