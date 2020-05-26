package com.rockstar.artifact.codegen.model;

import java.util.ArrayList;
import java.util.Collection;

public class TableDefinition implements Definition {

	private String name;
	private Collection<FieldDefinition> columns;
	
	public String getName() {
		return this.name;
	}

	public Collection<FieldDefinition> getColumns() {
		return columns;
	}

	public void setColumns(Collection<FieldDefinition> columns) {
		this.columns = columns;
	}
	
	public Collection<FieldDefinition> getIndexFields() {
		Collection<FieldDefinition> indexFields = new ArrayList<FieldDefinition> ();
		if (this.getColumns() != null && !this.getColumns().isEmpty()) {
			for (FieldDefinition currentField : this.getColumns()) {
				if (currentField.getIndex()) {
					indexFields.add(currentField);
				}
			}
		}
		return indexFields;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "Table Definition [name=" + name + ", columns=" + columns + "]";
	}

}
