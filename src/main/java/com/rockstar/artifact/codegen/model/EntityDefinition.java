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
	
	public Collection<FieldDefinition> getFieldsWithPrimitiveType() {
		Collection<FieldDefinition> primitiveFields = null;
		if (this.fields != null && !this.fields.isEmpty()) {
			primitiveFields = new ArrayList<FieldDefinition> ();
			for (FieldDefinition currentField : this.fields) {
				if (currentField.isPrimitiveType()) {
					primitiveFields.add(currentField);
				}
			}
		}
		return primitiveFields;
	}
	
	public Collection<FieldDefinition> getTypes(FieldType type) {
		Collection<FieldDefinition> types = null;
		if (this.fields != null && !this.fields.isEmpty()) {
			types = new ArrayList<FieldDefinition> ();
			for (FieldDefinition currentfield : this.fields) {
				if (currentfield.getType().equals(type)) {
					types.add(currentfield);
				}
			}
		}
		return types;
	}
	
	public Collection<FieldDefinition> getPrimitiveOrObjectFields() {
		Collection<FieldDefinition> primitiveOrObjectFields = null;
		if (this.fields != null && !this.fields.isEmpty()) {
			primitiveOrObjectFields = new ArrayList<FieldDefinition> ();
			for (FieldDefinition currentfield : this.fields) {
				if (currentfield.isPrimitiveType() || currentfield.isObjectType()) {
					primitiveOrObjectFields.add(currentfield);
				}
			}
		}
		return primitiveOrObjectFields;
	}
	
	public boolean getArrayType() {
		return this.hasFieldType(FieldType.Array);
	}
	
	public boolean getDateType() {
		return this.hasFieldType(FieldType.LocalDate);
	}
	
	public boolean getDateTimeType() {
		return this.hasFieldType(FieldType.DateTime);
	}
	
	public boolean hasFieldType(FieldType type) {
		Collection<FieldDefinition> types = this.getTypes(type);
		return (types != null && !types.isEmpty());
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
