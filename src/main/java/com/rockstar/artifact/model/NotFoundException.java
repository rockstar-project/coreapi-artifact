package com.rockstar.artifact.model;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1825806169309983784L;
	
	public NotFoundException(String name, String id) {
		super(name + " not found: " + id);
	}
}
