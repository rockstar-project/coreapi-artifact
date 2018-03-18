package com.rockstar.artifact.model;

import java.util.HashMap;
import java.util.Map;

public class ProjectTemplateRegistry {

	private Map<String, ProjectTemplate> templateCache;

	public ProjectTemplateRegistry() {
		this.templateCache = new HashMap<String, ProjectTemplate>();
	}

	public void registerTemplate(String key, ProjectTemplate definition) {
		this.templateCache.put(key, definition);
	}

	public ProjectTemplate lookup(String name) throws NotFoundException {
		ProjectTemplate projectTemplate = this.templateCache.get(name);
		if (projectTemplate == null) {
			throw new NotFoundException("project template", name);
		}
		return projectTemplate;
	}

}
