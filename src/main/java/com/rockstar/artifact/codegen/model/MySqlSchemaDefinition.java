package com.rockstar.artifact.codegen.model;

import java.util.Collection;

public class MySqlSchemaDefinition implements Definition {

	private String name;
	private Collection<MySqlTableDefinition> tables;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Collection<MySqlTableDefinition> getTables() {
		return tables;
	}

	public void setTables(Collection<MySqlTableDefinition> tables) {
		this.tables = tables;
	}

}
