package com.rockstar.artifact.codegen.model;

import java.util.Collection;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class EntityDefinition {
	
	private String name;
	private Collection<FieldDefinition> fields;
	private Collection<RelationshipDefinition> relationships;
	
	public EntityDefinition() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Collection<FieldDefinition> getFields() {
		return fields;
	}

	public void setFields(Collection<FieldDefinition> fields) {
		this.fields = fields;
	}

	public Collection<RelationshipDefinition> getRelationships() {
		return relationships;
	}

	public void setRelationships(Collection<RelationshipDefinition> relationships) {
		this.relationships = relationships;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
