package com.rockstar.artifact.model;

import java.util.HashMap;
import java.util.Map;

public class TemplateRegistry {

	private Map<String, TemplateDirectory> templateCache;

	public TemplateRegistry() {
		this.templateCache = new HashMap<String, TemplateDirectory>();
	}

	public void registerTemplate(String key, TemplateDirectory definition) {
		this.templateCache.put(key, definition);
	}

	public TemplateDirectory lookup(String name) throws NotFoundException {
		TemplateDirectory projectTemplate = this.templateCache.get(name);
		if (projectTemplate == null) {
			throw new NotFoundException("project template", name);
		}
		return projectTemplate;
	}

}
