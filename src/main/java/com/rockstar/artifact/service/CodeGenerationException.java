package com.rockstar.artifact.service;

public class CodeGenerationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
    private String template = null;
    
	public CodeGenerationException(String message) {
		super(message);
	}
	
	public CodeGenerationException(String message, String template) {
		super(message);
		this.template = template;
	}
	
	public CodeGenerationException(Throwable throwable) {
		super(throwable);
	}
	
	public String getTemplate() {
		return this.template;
	}

}
