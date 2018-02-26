package com.rockstar.artifact.codegen.model;

import java.util.HashMap;
import java.util.Map;

public class SampleDataDefinition {
	
	private Map<String, Object> sample;
	
	public SampleDataDefinition() {
		this.sample = new HashMap<String, Object>();
	}
	
	public void setData(String name, Object value) {
		this.sample.put(name, value);
	}
	
	public Map<String, Object> getData() {
		return this.sample;
	}
	
}
