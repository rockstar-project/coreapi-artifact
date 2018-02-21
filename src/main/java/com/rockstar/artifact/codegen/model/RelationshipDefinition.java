package com.rockstar.artifact.codegen.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RelationshipDefinition {
	
	private EntityDefinition relatedEntity;
	private RelationshipType type;
	
	public RelationshipDefinition() {
	}
	
	public EntityDefinition getRelatedEntity() {
		return relatedEntity;
	}
	
	public void setRelatedEntity(EntityDefinition relatedEntity) {
		this.relatedEntity = relatedEntity;
	}

	public RelationshipType getType() {
		return type;
	}

	public void setType(RelationshipType type) {
		this.type = type;
	}
	
	public boolean isOneToOne() {
		return (this.type != null && this.type.equals(RelationshipType.OneToOne));
	}
	
	public boolean isOneToMany() {
		return (this.type != null && this.type.equals(RelationshipType.OneToMany));
	}
	
	public boolean isManyToOne() {
		return (this.type != null && this.type.equals(RelationshipType.ManyToOne));
	}
	
	public boolean isManyToMany() {
		return (this.type != null && this.type.equals(RelationshipType.ManyToMany));
	}
	
	public boolean isEmbeddedObject() {
		return (this.type != null && this.type.equals(RelationshipType.Embedded_Object));
	}
	
	public boolean isEmbeddedList() {
		return (this.type != null && this.type.equals(RelationshipType.Embedded_List));
	}
	
	public boolean isEmbeddable() {
		return (this.type != null && this.type.equals(RelationshipType.Embeddable));
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
