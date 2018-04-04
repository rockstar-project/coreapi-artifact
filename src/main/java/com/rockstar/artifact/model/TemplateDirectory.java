package com.rockstar.artifact.model;

import java.util.Collection;

public class TemplateDirectory {
	
	private Collection<Template> files;
	
	public TemplateDirectory() {
	}

	public Collection<Template> getFiles() {
		return files;
	}

	public void setFiles(Collection<Template> files) {
		this.files = files;
	}

}
