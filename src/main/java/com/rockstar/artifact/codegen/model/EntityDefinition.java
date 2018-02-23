package com.rockstar.artifact.codegen.model;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

public class EntityDefinition implements Definition {
	
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
	
	public Collection<FieldDefinition> getEnumFields() {
		Collection<FieldDefinition> enumfields = null;
		
		if (this.fields != null && !this.fields.isEmpty()) {
			enumfields = new ArrayList<FieldDefinition> ();
			for (FieldDefinition current : this.fields) {
				if (current.getEnumeration()) {
					enumfields.add(current);
				}
			}
		}
		return enumfields;
	}
	
	public Collection<FieldDefinition> getUniquefields() {
		Collection<FieldDefinition> uniquefields = null;
		
		if (this.fields != null && !this.fields.isEmpty()) {
			uniquefields = new ArrayList<FieldDefinition> ();
			for (FieldDefinition current : this.fields) {
				if (current.getUnique()) {
					uniquefields.add(current);
				}
			}
		}
		return uniquefields;
	}
	
	public String getUniqueFieldsAndSeparated() {
		Collection<FieldDefinition> uniquefields = null;
		String[] uniquefieldNames = null;
		String uniquefieldNamesSeparatedByAnd = null;
		int index = 0;
		uniquefields = this.getUniquefields();
		uniquefieldNames = new String[uniquefields.size()];
		
		if (uniquefields != null && !uniquefields.isEmpty()) {
			for (FieldDefinition currentfield : uniquefields) {
				uniquefieldNames[index] = currentfield.getName();
				index = index + 1;
			}
			uniquefieldNamesSeparatedByAnd = StringUtils.join(uniquefieldNames, "And");
		}
		return uniquefieldNamesSeparatedByAnd;
	}
	
	public Collection<FieldDefinition> getTypes(FieldType type) {
		Collection<FieldDefinition> fieldDefinitions = new ArrayList<FieldDefinition> ();
		for (FieldDefinition currentfield : this.fields) {
			if (currentfield.getType().equals(type)) {
				fieldDefinitions.add(currentfield);
			}
		}
		return fieldDefinitions;
	}
	
	public Boolean isStringTypes() {
		return !this.getTypes(FieldType.String).isEmpty();
	}
	
	public Boolean isBooleanTypes() {
		return !this.getTypes(FieldType.Boolean).isEmpty();
	}
	
	public Boolean isShortTypes() {
		return !this.getTypes(FieldType.Short).isEmpty();
	}
	
	public Boolean isIntegerTypes() {
		return !this.getTypes(FieldType.Integer).isEmpty();
	}
	
	public Boolean isLongTypes() {
		return !this.getTypes(FieldType.Long).isEmpty();
	}
	
	public Boolean isDoubleTypes() {
		return !this.getTypes(FieldType.Double).isEmpty();
	}
	
	public Boolean isFloatTypes() {
		return !this.getTypes(FieldType.Float).isEmpty();
	}
	
	public Boolean isDateTypes() {
		return !this.getTypes(FieldType.LocalDate).isEmpty();
	}
	
	public Boolean isDateTimeTypes() {
		return !this.getTypes(FieldType.DateTime).isEmpty();
	}
	
	public Boolean isArrayTypes() {
		return !this.getTypes(FieldType.Array).isEmpty();
	}
	
	public Boolean isObjectTypes() {
		return !this.getTypes(FieldType.Object).isEmpty();
	}

	public String toString() {
		String separator = null;
		StringBuilder entityStringBuilder = new StringBuilder();
		
		entityStringBuilder.append("Entity Name: " + this.name + "\n");
		entityStringBuilder.append("Fields: ");
		
		if (this.fields != null && !this.fields.isEmpty()) {
			separator = "";
			for (FieldDefinition current: this.fields) {
				entityStringBuilder.append(separator);
				entityStringBuilder.append(current);
				separator = ", ";
			}
		} else {
			entityStringBuilder.append(" - 0 found");
		}
		
		entityStringBuilder.append("\nRelationships: " + "\n");
		if (this.relationships != null && !this.relationships.isEmpty()) {
			separator = "";
			for (RelationshipDefinition current: this.relationships) {
				entityStringBuilder.append(separator);
				entityStringBuilder.append(current);
				separator = ",";
			}
		} else {
			entityStringBuilder.append(" - 0 found");
		}
		entityStringBuilder.append("\n");
		
		
		return entityStringBuilder.toString();
	}
}
