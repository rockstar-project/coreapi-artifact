package com.ibmcloud.kickster.model;

public class InvalidSchemaException extends RuntimeException {

	private static final long serialVersionUID = 1825806169309983784L;
	
	public InvalidSchemaException(String message) {
		super(message);
	}

}
