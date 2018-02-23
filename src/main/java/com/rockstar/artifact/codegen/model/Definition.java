package com.rockstar.artifact.codegen.model;

public interface Definition {
	
	public enum Type {
		General,
		Config,
		Controller,
		Search,
		Enum,
		Resource,
		Service,
		Model,
		Entity,
		Repository,
		Security 
	};
	
	public String getName();

}
