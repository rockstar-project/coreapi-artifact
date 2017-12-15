package com.ibmcloud.kickster.model;

import java.util.Collection;
import java.util.List;

public class TemplateDefinition {
	
	private String slug;
	private Collection<String> tags;
	private List<TemplateFile> files;
	private List<Directory> directories;
	
	public TemplateDefinition() {
	}
	
	public String getSlug() {
		return this.slug;
	}
	
	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Collection<String> getTags() {
		return tags;
	}

	public void setTags(Collection<String> tags) {
		this.tags = tags;
	}

	public List<TemplateFile> getFiles() {
		return files;
	}

	public void setFiles(List<TemplateFile> files) {
		this.files = files;
	}

	public List<Directory> getDirectories() {
		return directories;
	}

	public void setDirectories(List<Directory> directories) {
		this.directories = directories;
	}
	
	public Directory getDirectoryByPath(String path) {
		Directory directory = null;
		
		for (Directory currentDir: this.getDirectories()) {
			if (currentDir.getPath().equalsIgnoreCase(path)) {
				directory = currentDir;
				break;
			}
		}
		
		return directory;
	}
	
}
