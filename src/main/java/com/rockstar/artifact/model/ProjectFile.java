package com.rockstar.artifact.model;

public class ProjectFile {
	
	private String baseDir;
	private String template;
	private String filename;
	private String rule;
	private String component;
	private String namingConvention;
	private String packageDir;
	
	public ProjectFile() {
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}
	
	public String getNamingConvention() {
		return namingConvention;
	}

	public void setNamingConvention(String namingConvention) {
		this.namingConvention = namingConvention;
	}
	
	public String getPackageDir() {
		return packageDir;
	}

	public void setPackageDir(String packageDir) {
		this.packageDir = packageDir;
	}

}
