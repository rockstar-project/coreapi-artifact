package com.rockstar.artifact.codegen.model;

public class RelationshipDefinition {
	
	private EntityDefinition parent;
	private EntityDefinition child;
	private RelationshipType type;
	
	public RelationshipDefinition() {
	}

	public EntityDefinition getParent() {
		return parent;
	}

	public void setParent(EntityDefinition parent) {
		this.parent = parent;
	}

	public EntityDefinition getChild() {
		return child;
	}

	public void setChild(EntityDefinition child) {
		this.child = child;
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
		StringBuilder relationshipStringBuilder = new StringBuilder();
		
		relationshipStringBuilder.append("Type: " + this.type + "\n");
		relationshipStringBuilder.append("parent: " + (this.parent != null ? this.parent.getName() : "undefined") + "\n");
		relationshipStringBuilder.append("child: " + (this.child != null ? this.child.getName() : "undefined") + "\n");
		relationshipStringBuilder.append("\n");
		
		return relationshipStringBuilder.toString();
	}
}
