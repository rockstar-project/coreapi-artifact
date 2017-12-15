package com.rockstar.artifact.model;

import java.util.List;

public class TemplateDefinitionRegistry {
	
	private List<TemplateDefinition> definitions;
	
	public TemplateDefinitionRegistry() {
	}
	
	public void registerTemplateDefinitions(List<TemplateDefinition> definitions) {
		this.definitions = definitions;
	}
	
	public TemplateDefinition lookup(String name) throws NotFoundException {
		TemplateDefinition workspace = null;
		if (this.definitions != null && !definitions.isEmpty()) {
			for (TemplateDefinition current : this.definitions) {
				if (current.getSlug().equalsIgnoreCase(name)) {
					workspace = current;
					break;
				}
			}
		}
		if (workspace == null) {
	    	throw new NotFoundException("template definition", name);
		}
		return workspace;
	}
}