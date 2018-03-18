package com.rockstar.artifact.codegen.model;

public interface Definition {
	
	public enum Type {
		General,
		Config,
		Messaging,
		Controller,
		Search,
		Enum,
		Resource,
		Service,
		Model,
		Entity,
		Repository,
		Security,
		MySqlSchema,
		ApiTest,
		UnitTest
	};
	
	public String getName();

}
