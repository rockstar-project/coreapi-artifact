package com.rockstar.artifact.model;

import java.util.List;

public class Directory {
	
	public static final String ROOT = "root";
	private static final String DEFAULT_PATH = ROOT;
	private static final String DEFAULT_BASE = "";
	private static final Type DEFAULT_TYPE = Type.source;
	private static final ResolutionMethod DEFAULT_RESOLUTION = ResolutionMethod.Static;
	
	private String base;
    private String path;
    private Type type;
    private ResolutionMethod resolution;
    private List<TemplateFile> files;

    public Directory() {
    	this.base = DEFAULT_BASE;
    	this.path = DEFAULT_PATH;
    	this.type = DEFAULT_TYPE;
    	this.resolution = DEFAULT_RESOLUTION;
    }

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<TemplateFile> getFiles() {
		return this.files;
	}

	public void setFiles(List<TemplateFile> files) {
		this.files = files;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public ResolutionMethod getResolution() {
		return this.resolution;
	}

	public void setResolution(ResolutionMethod resolution) {
		this.resolution = resolution;
	}

	public String toString() {
		return "base=" + this.base + ", path=" + this.path + ", resolution=" + this.resolution + ", files=" + this.files;
	}

}
