package com.rockstar.artifact.model;

public class TemplateFile {
	
	private static final String DEFAULT_NAME = "";
	private static final NamingStrategy DEFAULT_NAMING_STRATEGY = NamingStrategy.Capitalize;
	
	private String slug;
	private String name;
	private NamingStrategy namingStrategy;
	private String rule;
	
	public TemplateFile() {
		this.name = DEFAULT_NAME;
		this.namingStrategy = DEFAULT_NAMING_STRATEGY;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public NamingStrategy getNamingStrategy() {
		return namingStrategy;
	}

	public void setNamingStrategy(NamingStrategy namingStrategy) {
		this.namingStrategy = namingStrategy;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	
}
