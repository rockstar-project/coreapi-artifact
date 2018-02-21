package com.rockstar.artifact.service;

import java.util.HashMap;
import java.util.Map;

import com.rockstar.artifact.model.NotFoundException;
import com.rockstar.artifact.model.TemplateDefinition;

public class TemplateDefinitionRegistry {
	
	private Map<String, TemplateDefinition> definitionCache;
	
	public TemplateDefinitionRegistry() {
		this.definitionCache = new HashMap<String, TemplateDefinition> ();
	}
	
	public void registerTemplateDefinition(String key, TemplateDefinition definition) {
		this.definitionCache.put(key, definition);
	}
	
	public TemplateDefinition lookup(String name) throws NotFoundException {
		TemplateDefinition workspace = this.definitionCache.get(name);
		if (workspace == null) {
			throw new NotFoundException("template definition", name);
		}
		return workspace;
	}
}