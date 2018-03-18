package com.rockstar.artifact.model;

import com.rockstar.artifact.codegen.model.MicroserviceDefinition;

public class Project {
	
	private Model model;
	private MicroserviceDefinition specDefinitions;
	
	public Project() {
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public MicroserviceDefinition getSpecDefinitions() {
		return specDefinitions;
	}

	public void setSpecDefinitions(MicroserviceDefinition specDefinitions) {
		this.specDefinitions = specDefinitions;
	}
}
