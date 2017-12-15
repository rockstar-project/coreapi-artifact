package com.ibmcloud.kickster.model;

import java.util.List;
import java.util.UUID;

public class GeneratedProject {
	
	private String id;
	private String slug;
	private List<GeneratedFile> files;
	
	public GeneratedProject() {
		this.id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSlug() {
		return this.slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public List<GeneratedFile> getFiles() {
		return this.files;
	}

	public void setFiles(List<GeneratedFile> files) {
		this.files = files;
	}

}
