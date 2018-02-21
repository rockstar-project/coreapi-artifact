package com.rockstar.artifact.codegen.model;

import java.util.Collection;

public class ResourceDefinition {
	
	private String name;
	private Collection<AttributeDefinition> attributes;
	private Collection<ResourceDefinition> subresources;
	
	public ResourceDefinition() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<AttributeDefinition> getAttributes() {
		return attributes;
	}

	public void setAttributes(Collection<AttributeDefinition> attributes) {
		this.attributes = attributes;
	}

	public Collection<ResourceDefinition> getSubresources() {
		return subresources;
	}

	public void setSubresources(Collection<ResourceDefinition> subresources) {
		this.subresources = subresources;
	}
	
	public AttributeDefinition getAttributeByName(String name) {
		AttributeDefinition attribute = null;
		for (AttributeDefinition currentAttribute : this.attributes) {
			if (currentAttribute.getName().equalsIgnoreCase(name)) {
				attribute = currentAttribute;
				break;
			}
		}
		return attribute;
	}
	
	public String toString() {
		StringBuilder attributeStringBuilder = new StringBuilder();
		
		attributeStringBuilder.append(this.name);
		attributeStringBuilder.append("\nAttributes");
		attributeStringBuilder.append("\n=========== \n");
		if (this.attributes != null && !this.attributes.isEmpty()) {
			
			for (AttributeDefinition current: this.attributes) {
				attributeStringBuilder.append(current);
				attributeStringBuilder.append("\n");
			}
		} else {
			attributeStringBuilder.append(" - 0 found");
		}
		
		attributeStringBuilder.append("\nSub Resources");
		attributeStringBuilder.append("\n=========== \n");
		if (this.subresources != null && !this.subresources.isEmpty()) {
			for (ResourceDefinition subresource: this.subresources) {
				attributeStringBuilder.append(subresource);
				attributeStringBuilder.append("\n");
			}
		} else {
			attributeStringBuilder.append(" - 0 found");
		}
		
		return attributeStringBuilder.toString();
	}


}
