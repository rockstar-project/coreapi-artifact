package com.rockstar.artifact.codegen.model;

public class UnitTestDefinition implements Definition {
	
	private String name;
	private SampleDataDefinition data;
	
	public UnitTestDefinition() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SampleDataDefinition getData() {
		return data;
	}

	public void setData(SampleDataDefinition data) {
		this.data = data;
	}
}
