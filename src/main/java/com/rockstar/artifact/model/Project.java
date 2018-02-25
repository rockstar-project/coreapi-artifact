package com.rockstar.artifact.model;

import com.rockstar.artifact.codegen.model.SpecDefinitions;

public class Project {
	
	private Model model;
	private SpecDefinitions specDefinitions;
	
	public Project() {
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public SpecDefinitions getSpecDefinitions() {
		return specDefinitions;
	}

	public void setSpecDefinitions(SpecDefinitions specDefinitions) {
		this.specDefinitions = specDefinitions;
	}
}
