package com.rockstar.artifact.codegen.model;

import java.util.Collection;

public class DatabaseSchemaDefinition implements Definition {

	private String name;
	private Collection<TableDefinition> tables;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Collection<TableDefinition> getTables() {
		return tables;
	}

	public void setTables(Collection<TableDefinition> tables) {
		this.tables = tables;
	}

}
