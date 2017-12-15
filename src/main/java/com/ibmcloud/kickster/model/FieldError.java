package com.ibmcloud.kickster.model;

public class FieldError {
	
	private String field;
	private String text;
	
	public FieldError(String field, String text) {
		this.field = field;
		this.text = text;
	}

	public String getField() {
		return this.field;
	}

	public String getText() {
		return this.text;
	}

}
