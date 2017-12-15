package com.rockstar.artifact.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {
	
private String error;
	
	@JsonProperty(value="error_description")
	private String description;
	private List<FieldError> fields;

	public Error(String error, String description) {
		this.error = error;
		this.description = description;
		this.fields = new ArrayList<FieldError> ();
	}
	
	public String getError() {
		return this.error;
	}
	
	public String getDescription() {
		return this.description;
	}

	public List<FieldError> getFields() {
		return this.fields;
	}

	public void setFields(List<FieldError> fields) {
		this.fields = fields;
	}
}
