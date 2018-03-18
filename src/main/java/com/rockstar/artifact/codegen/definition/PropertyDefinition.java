package com.rockstar.artifact.codegen.definition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import com.rockstar.artifact.codegen.model.ConstraintDefinition.Constraint;

public class PropertyDefinition {
	
	private String name;
	private PrimitiveType type;
	private Object defaultValue;
	private Boolean unique;
	private Collection<String> dependencies;
	private Collection<ConstraintDefinition> constraints;
	
	public PropertyDefinition() {
		this.constraints = new ArrayList<ConstraintDefinition> ();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PrimitiveType getType() {
		return type;
	}

	public void setType(PrimitiveType type) {
		this.type = type;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Boolean getUnique() {
		return unique;
	}

	public void setUnique(Boolean unique) {
		this.unique = unique;
	}

	public Collection<ConstraintDefinition> getConstraints() {
		return constraints;
	}

	public void setConstraints(Collection<ConstraintDefinition> constraints) {
		this.constraints = constraints;
	}

	public Collection<String> getDependencies() {
		return dependencies;
	}

	public void setDependencies(Collection<String> dependencies) {
		this.dependencies = dependencies;
	}

	public String toString() {
		StringBuilder propertyStringBuilder = new StringBuilder();
		
		propertyStringBuilder.append(this.name);
		propertyStringBuilder.append(" (" + this.type + ")");
		if (this.unique != null) {
			propertyStringBuilder.append(", unique = yes\n");
		}
		if (this.defaultValue != null) {
			propertyStringBuilder.append(", default value = " + (String) this.defaultValue);
		}
		if (this.constraints != null && !this.constraints.isEmpty()) {
			propertyStringBuilder.append(" - ");
			
			String separator = "";
			for (ConstraintDefinition current : this.constraints) {
				propertyStringBuilder.append(separator);
				propertyStringBuilder.append(current);
				separator = ", ";
			}
		}
		
		return propertyStringBuilder.toString();
	}
	
}
