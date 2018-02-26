package com.rockstar.artifact.codegen.model;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class RepositoryDefinition implements Definition {
	
	private String name;
	private Collection<FieldDefinition> uniquefields;
	private SearchDefinition search;
	
	public RepositoryDefinition() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<FieldDefinition> getUniquefields() {
		return uniquefields;
	}

	public void setUniquefields(Collection<FieldDefinition> uniquefields) {
		this.uniquefields = uniquefields;
	}

	public SearchDefinition getSearch() {
		return search;
	}

	public void setSearch(SearchDefinition search) {
		this.search = search;
	}

	public String getUniqueFieldsMethodArguments() {
		Collection<FieldDefinition> uniquefields = null;
		String[] methodArguments = null;
		String uniquefieldMethodArguments = null;
		int index = 0;
		uniquefields = this.getUniquefields();
		
		if (uniquefields != null && !uniquefields.isEmpty()) {
			methodArguments = new String[uniquefields.size()];
			
			for (FieldDefinition currentfield : uniquefields) {
				methodArguments[index] = String.format("%s %s", currentfield.getType(), currentfield.getName());
				index = index + 1;
			}
			uniquefieldMethodArguments = StringUtils.join(methodArguments, ", ");
		}
		
		return uniquefieldMethodArguments;
	}
	
	public String getUniqueFieldsGettersCommaSeparated() {
		// TODO
		return "";
	}
	
	public String getUniqueFieldsAndSeparated() {
		Collection<FieldDefinition> uniquefields = null;
		String[] uniquefieldNames = null;
		String uniquefieldNamesSeparatedByAnd = null;
		int index = 0;
		uniquefields = this.getUniquefields();
		
		if (uniquefields != null && !uniquefields.isEmpty()) {
			uniquefieldNames = new String[uniquefields.size()];
			for (FieldDefinition currentfield : uniquefields) {
				uniquefieldNames[index] = WordUtils.capitalize(currentfield.getName());
				index = index + 1;
			}
			uniquefieldNamesSeparatedByAnd = StringUtils.join(uniquefieldNames, "And");
		}
		return uniquefieldNamesSeparatedByAnd;
	}

	
	public String toString() {
		String separator = null;
		StringBuilder entityStringBuilder = new StringBuilder();
		
		entityStringBuilder.append("Repository Name: " + this.name + "\n");
		entityStringBuilder.append("Unique Fields: ");
		
		if (this.uniquefields != null && !this.uniquefields.isEmpty()) {
			separator = "";
			for (FieldDefinition current: this.uniquefields) {
				entityStringBuilder.append(separator);
				entityStringBuilder.append(current);
				separator = ", ";
			}
		} else {
			entityStringBuilder.append(" - 0 found");
		}
		
		entityStringBuilder.append("\n");
		
		return entityStringBuilder.toString();
	}

}
