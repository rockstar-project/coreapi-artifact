package com.rockstar.artifact.codegen.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;

public class PojoDefinition {
	
	private String name;
	private Map<String, PropertyDefinition> properties;
	
	public PojoDefinition() {
		this.properties = new HashMap<String, PropertyDefinition> ();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addPropertyDefinition(String name, PropertyDefinition definition) {
		definition.setName(name);
		this.properties.put(name,  definition);
	}
	
	public PropertyDefinition getPropertyDefinition(String name) {
		return this.properties.get(name);
	}
	
	public Map<String, PropertyDefinition> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, PropertyDefinition> properties) {
		this.properties = properties;
	}

	public Set<String> getFieldDeclarations() {
		Set<String> fieldDeclarations = new HashSet<String> ();
		String fieldDeclarationTemplate = "private %s %s;";
	
		for (Entry<String, PropertyDefinition> propertyDefinitionEntry : this.properties.entrySet()) {
			fieldDeclarations.add(String.format(fieldDeclarationTemplate, propertyDefinitionEntry.getValue().getType(), propertyDefinitionEntry.getKey()));
		}
		return fieldDeclarations;
	}
	
	public Set<Map.Entry<String, PropertyDefinition>> getFields() {
		Set<Map.Entry<String,PropertyDefinition>> entrySet = new HashSet<Map.Entry<String, PropertyDefinition>> ();
		for (Entry<String, PropertyDefinition> propertyDefinitionEntry : this.properties.entrySet()) {
			entrySet.add(propertyDefinitionEntry);
		}
		return entrySet;
	}
	
	public Set<Map.Entry<String, PropertyDefinition>> getRequiredFields() {
		Set<Map.Entry<String,PropertyDefinition>> entrySet = new HashSet<Map.Entry<String, PropertyDefinition>> ();
		for (Entry<String, PropertyDefinition> propertyDefinitionEntry : this.properties.entrySet()) {
			if (propertyDefinitionEntry.getValue().getRequired()) {
				entrySet.add(propertyDefinitionEntry);
			}
		}
		return entrySet;
	}
	
	public Set<Map.Entry<String, PropertyDefinition>> getNonRequiredFields() {
		Set<Map.Entry<String,PropertyDefinition>> entrySet = new HashSet<Map.Entry<String, PropertyDefinition>> ();
		for (Entry<String, PropertyDefinition> propertyDefinitionEntry : this.properties.entrySet()) {
			if (!propertyDefinitionEntry.getValue().getRequired()) {
				entrySet.add(propertyDefinitionEntry);
			}
		}
		return entrySet;
	}
	
	public Set<Map.Entry<String,PropertyDefinition>> getEnumfields() {
		Set<Map.Entry<String,PropertyDefinition>> entrySet = new HashSet<Map.Entry<String, PropertyDefinition>> ();
		for (Entry<String, PropertyDefinition> propertyDefinitionEntry : this.properties.entrySet()) {
			if (propertyDefinitionEntry.getValue().getEnumeration()) {
				entrySet.add(propertyDefinitionEntry);
			}
		}
		return entrySet;
	}
	
	public Set<Map.Entry<String,PropertyDefinition>> getUniqueItems() {
		Set<Map.Entry<String,PropertyDefinition>> entrySet = new HashSet<Map.Entry<String, PropertyDefinition>> ();
		for (Entry<String, PropertyDefinition> propertyDefinitionEntry : this.properties.entrySet()) {
			if (propertyDefinitionEntry.getValue().getUnique()) {
				entrySet.add(propertyDefinitionEntry);
			}
		}
		
		return entrySet;
	}
	
	public Collection<PropertyDefinition> getRequiredProperties() {
		Collection<PropertyDefinition> requiredProperties = null;
		
		requiredProperties = new ArrayList<PropertyDefinition>();
		for (Entry<String, PropertyDefinition> propertyEntry : this.properties.entrySet()) {
			if (propertyEntry.getValue().getFilter()) {
				requiredProperties.add(propertyEntry.getValue());
			}
		}
		return requiredProperties;
	}
	
	public Collection<PropertyDefinition> getSearchFilters() {
		Collection<PropertyDefinition> searchfilters = null;
		
		searchfilters = new ArrayList<PropertyDefinition>();
		for (Entry<String, PropertyDefinition> propertyEntry : this.properties.entrySet()) {
			if (propertyEntry.getValue().getFilter()) {
				searchfilters.add(propertyEntry.getValue());
			}
		}
		return searchfilters;
	}
	
	public Collection<PropertyDefinition> getUniqueFields() {
		Collection<PropertyDefinition> uniquefields = null;
		
		uniquefields = new ArrayList<PropertyDefinition>();
		for (Entry<String, PropertyDefinition> propertyEntry : this.properties.entrySet()) {
			if (propertyEntry.getValue().getUnique()) {
				uniquefields.add(propertyEntry.getValue());
			}
		}
		
		return uniquefields;
	}
	
	public String getUniqueFieldsSeparatedByAnd() {
		Collection<PropertyDefinition> uniquefields = null;
		String[] uniquefieldNames = null;
		String uniquefieldNamesSeparatedByAnd = null;
		
		uniquefields = this.getUniqueFields();
		
		if (uniquefields != null && !uniquefields.isEmpty()) {
			uniquefieldNamesSeparatedByAnd = StringUtils.join(uniquefieldNames, "And");
		}
		return uniquefieldNamesSeparatedByAnd;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
