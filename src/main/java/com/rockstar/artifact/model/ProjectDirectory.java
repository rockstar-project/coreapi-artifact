package com.rockstar.artifact.model;

import java.util.Collection;

public class ProjectDirectory {
	
	private Collection<ProjectFile> files;
	
	public ProjectDirectory() {
	}

	public Collection<ProjectFile> getFiles() {
		return files;
	}

	public void setFiles(Collection<ProjectFile> files) {
		this.files = files;
	}

}
