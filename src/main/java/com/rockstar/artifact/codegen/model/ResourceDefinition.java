package com.rockstar.artifact.codegen.model;

import java.util.ArrayList;
import java.util.Collection;

public class ResourceDefinition implements Definition {
	
	private String name;
	private Collection<AttributeDefinition> attributes;
	private Collection<ResourceDefinition> subresources;
	private EntityDefinition entity;
	
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
	
	public EntityDefinition getEntity() {
		return entity;
	}

	public void setEntity(EntityDefinition entity) {
		this.entity = entity;
	}
	
	public Collection<ResourceDefinition> getSubresources() {
		return subresources;
	}

	public void setSubresources(Collection<ResourceDefinition> subresources) {
		this.subresources = subresources;
	}

	public Collection<AttributeDefinition> getAttributesWithPrimitiveType() {
		Collection<AttributeDefinition> primitiveAttributes = null;
		if (this.attributes != null && !this.attributes.isEmpty()) {
			primitiveAttributes = new ArrayList<AttributeDefinition> ();
			for (AttributeDefinition currentAttribute : this.attributes) {
				if (currentAttribute.isPrimitiveType()) {
					primitiveAttributes.add(currentAttribute);
				}
			}
		}
		return primitiveAttributes;
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
	
	public boolean getValidConstraint() {
		return this.hasConstraintType(ConstraintType.Valid);
	}
	
	public boolean getNotEmptyConstraint() {
		return this.hasConstraintType(ConstraintType.NotEmpty);
	}
	
	public boolean getNotNullConstraint() {
		return this.hasConstraintType(ConstraintType.NotNull);
	}
	
	public boolean getSizeConstraint() {
		return this.hasConstraintType(ConstraintType.Size);
	}
	
	public boolean getPatternConstraint() {
		return this.hasConstraintType(ConstraintType.Pattern);
	}
	
	public boolean getURLConstraint() {
		return this.hasConstraintType(ConstraintType.URL);
	}
	
	public boolean getListType() {
		return this.hasAttributeType(AttributeType.Array);
	}
	
	public boolean getDateType() {
		return this.hasAttributeType(AttributeType.LocalDate);
	}
	
	public boolean getDateTimeType() {
		return this.hasAttributeType(AttributeType.DateTime);
	}
	
	public boolean hasAttributeType(AttributeType type) {
		boolean hasType = false;
		for (AttributeDefinition currentAttribute : this.attributes) {
			if (currentAttribute.isType(type)) {
				hasType = true;
				break;
			}
		}
		return hasType;
	}
	
	public boolean getEmailConstraint() {
		return this.hasConstraintType(ConstraintType.Email);
	}
	
	public boolean hasConstraintType(ConstraintType type) {
		boolean hasConstraint = false;
		for (AttributeDefinition currentAttribute : this.attributes) {
			if (currentAttribute.hasConstraint(type)) {
				hasConstraint = true;
				break;
			}
		}
		return hasConstraint;
	}
	
	public String toString() {
		String separator = "";
		StringBuilder resourceStringBuilder = new StringBuilder();
		
		resourceStringBuilder.append("Resource Name: " + this.name + "\n");
		resourceStringBuilder.append("Attributes\n");

		if (this.attributes != null && !this.attributes.isEmpty()) {
			
			for (AttributeDefinition current: this.attributes) {
				resourceStringBuilder.append(separator);
				resourceStringBuilder.append(current);
				separator = "\n";
			}
		} else {
			resourceStringBuilder.append(" - 0 found");
		}
		
		resourceStringBuilder.append("\n");
		
		return resourceStringBuilder.toString();
	}


}
