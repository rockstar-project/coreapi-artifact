package com.rockstar.artifact.model;

import java.io.File;

import org.springframework.util.StringUtils;

public class GeneratedFile {
	
	private String slug;
	private String filename;
	private String path = "";
	private String content;
	
	public GeneratedFile() {
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public File toFile() throws Exception {
		
		File file = new File("");
		file.createNewFile();
		return file;
	}
	
	public String getFullname() {
		StringBuilder stringBuilder = new StringBuilder();
		
		if (StringUtils.hasText(this.path)) {
			stringBuilder.append(this.path);
			stringBuilder.append(File.separator);
		}
			
		stringBuilder.append(this.filename);
		return stringBuilder.toString();
	}
	
	
}
