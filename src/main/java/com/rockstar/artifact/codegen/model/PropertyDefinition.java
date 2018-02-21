package com.rockstar.artifact.codegen.model;

import java.util.Collection;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PropertyDefinition {
	
	private String name;
	private Boolean required;
	private Boolean readOnly;
	private Boolean unique;
	private Boolean enumeration;
	private String reference;
	private Boolean filter;
	private String minLength;
	private String maxLength;
	private String minSize;
	private String maxSize;
	private String pattern;
	private String type;
	private String format;
	private Object sampleData;
	private Object defaultValue;
	private Collection<Object> allowableValues;
	
	public PropertyDefinition() {
		this.required = Boolean.FALSE;
		this.readOnly = Boolean.FALSE;
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

	public Object getSampleData() {
		return sampleData;
	}

	public void setSampleData(Object sampleData) {
		this.sampleData = sampleData;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
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

	public Boolean getFilter() {
		return filter;
	}

	public void setFilter(Boolean filter) {
		this.filter = filter;
	}

	public Boolean getEnumeration() {
		return enumeration;
	}

	public void setEnumeration(Boolean enumeration) {
		this.enumeration = enumeration;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getMinLength() {
		return minLength;
	}

	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}

	public String getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	public String getMinSize() {
		return minSize;
	}

	public void setMinSize(String minSize) {
		this.minSize = minSize;
	}

	public String getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(String maxSize) {
		this.maxSize = maxSize;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Collection<Object> getAllowableValues() {
		return allowableValues;
	}

	public void setAllowableValues(Collection<Object> allowableValues) {
		this.allowableValues = allowableValues;
	}
	
	public boolean hasAllowableValues() {
		return (this.allowableValues != null && this.allowableValues.size() > 0);
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	

}
