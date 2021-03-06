package com.rockstar.artifact.codegen.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MicroserviceDefinition {
	
	private Map<Definition.Type, Collection<Definition>> definitions;
	
	public MicroserviceDefinition() {
		this.definitions = new HashMap<Definition.Type, Collection<Definition>> ();
	}

	public void withDefinitions(Definition.Type type, Collection<Definition> definitions) {
		for (Definition currentDefinition : this.getDefinitions(type)) {
			this.withDefinition(type, currentDefinition);
		}
	}
	
	public void withDefinition(Definition.Type type, Definition definitionItem) {
		if (this.getDefinitions(type) != null) {
			this.getDefinitions(type).add(definitionItem);
		} else {
			Collection<Definition> definitions = new ArrayList<Definition> ();
			definitions.add(definitionItem);
			this.definitions.put(type, definitions);
		}
	}
	
	public Collection<Definition> getDefinitions(Definition.Type type) {
		return this.definitions.get(type);
	}
	
	public String toString() {
		StringBuilder definitionStringBuilder = new StringBuilder();
		
		for (Entry<Definition.Type, Collection<Definition>> definitionEntry: this.definitions.entrySet()) {
			if (definitionEntry.getValue() != null && !definitionEntry.getValue().isEmpty()) {
				for (Definition currentDefinition : definitionEntry.getValue()) {
					definitionStringBuilder.append(currentDefinition);
					definitionStringBuilder.append("\n==============================\n");
				}
			}
		}
		
		return definitionStringBuilder.toString();
	}

}
