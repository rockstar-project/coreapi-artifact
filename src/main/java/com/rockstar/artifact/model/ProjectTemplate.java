package com.rockstar.artifact.model;

import java.util.Collection;

public class ProjectTemplate {
	
	private Collection<ProjectFile> files;
	
	public ProjectTemplate() {
	}

	public Collection<ProjectFile> getFiles() {
		return files;
	}

	public void setFiles(Collection<ProjectFile> files) {
		this.files = files;
	}

}
