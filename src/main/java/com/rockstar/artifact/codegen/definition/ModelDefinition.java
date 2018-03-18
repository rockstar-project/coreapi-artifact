package com.rockstar.artifact.codegen.definition;

import java.util.Collection;

import com.rockstar.artifact.codegen.model.Definition;

public class ModelDefinition implements Definition {
	
	private String name;
	private Collection<PropertyDefinition> properties;
	private Collection<ModelDefinition> objects;
	private Collection<ModelDefinition> collections;
	
	public ModelDefinition() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<PropertyDefinition> getProperties() {
		return properties;
	}

	public void setProperties(Collection<PropertyDefinition> properties) {
		this.properties = properties;
	}

	public Collection<ModelDefinition> getObjects() {
		return objects;
	}

	public void setObjects(Collection<ModelDefinition> objects) {
		this.objects = objects;
	}

	public Collection<ModelDefinition> getCollections() {
		return collections;
	}

	public void setCollections(Collection<ModelDefinition> collections) {
		this.collections = collections;
	}

	public String toString() {
		StringBuilder modelStringBuilder = new StringBuilder();
		String separator = "";
		
		modelStringBuilder.append("Model Name: " + this.name + "\n");
		if (this.properties != null) {
			modelStringBuilder.append("Properties:\n");
			for (PropertyDefinition current : this.properties) {
				modelStringBuilder.append(separator);
				modelStringBuilder.append(current);
				separator = "\n";
			}
		}
		separator = "";
		if (this.objects != null) {
			modelStringBuilder.append("\nObjects:");
			for (ModelDefinition current : this.objects) {
				modelStringBuilder.append(separator);
				modelStringBuilder.append(current);
				separator = "\n";
			}
		}
		if (this.collections != null) {
			modelStringBuilder.append("\nCollections:");
			modelStringBuilder.append(this.collections + "\n");
		}
		
		return modelStringBuilder.toString();
	}
	
	
}
