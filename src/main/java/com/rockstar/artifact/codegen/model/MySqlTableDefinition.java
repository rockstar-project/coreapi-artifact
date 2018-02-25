package com.rockstar.artifact.codegen.model;

import java.util.Collection;

public class MySqlTableDefinition implements Definition {

	private String name;
	private Collection<MySqlColumnDefinition> columns;
	
	public String getName() {
		return this.name;
	}

	public Collection<MySqlColumnDefinition> getColumns() {
		return columns;
	}

	public void setColumns(Collection<MySqlColumnDefinition> columns) {
		this.columns = columns;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "MySql Table Definition [name=" + name + ", columns=" + columns + "]";
	}

}
