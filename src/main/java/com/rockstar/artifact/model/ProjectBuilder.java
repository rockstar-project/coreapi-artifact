package com.rockstar.artifact.model;

import com.rockstar.artifact.codegen.model.SpecDefinitions;
import com.rockstar.artifact.util.CheckUtils;

public class ProjectBuilder {
	
	private Model model;
	private SpecDefinitions specs;

	public ProjectBuilder() {
		this.model = new Model();
	}
	
	public ProjectBuilder withArchitecture(String architecture) {
		CheckUtils.checkArgumentNotNullOrEmpty(architecture);
		this.model.setArchitecture(architecture);
		
		return this;
	}
	
	public ProjectBuilder withLanguage(SelectedValue language) {
		if (language != null) {
			this.model.setLanguageValue(language.getValue());
			this.model.setLanguageVersion(language.getVersion());
		}
		return this;
	}
	
	public ProjectBuilder withFramework(SelectedValue framework) {
		if (framework != null) {
			this.model.setFrameworkValue(framework.getValue());
			this.model.setFrameworkVersion(framework.getVersion());
		}
		return this;
	}
	
	public ProjectBuilder withDatastore(SelectedValue option) {
		if (option != null) {
			this.model.setDatastoreValue(option.getValue());
			this.model.setDatastoreVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectBuilder withHttp(SelectedValue option) {
		if (option != null) {
			this.model.setHttpValue(option.getValue());
			this.model.setHttpVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectBuilder withMessaging(SelectedValue option) {
		if (option != null) {
			this.model.setMessagingValue(option.getValue());
			this.model.setMessagingVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectBuilder withDiscovery(SelectedValue option) {
		if (option != null) {
			this.model.setDiscoveryValue(option.getValue());
			this.model.setDiscoveryVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectBuilder withTracing(SelectedValue option) {
		if (option != null) {
			this.model.setTracingValue(option.getValue());
			this.model.setTracingVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectBuilder withMonitoring(SelectedValue option) {
		if (option != null) {
			this.model.setMonitoringValue(option.getValue());
			this.model.setMonitoringVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectBuilder withSecurity(SelectedValue option) {
		if (option != null) {
			this.model.setSecurityValue(option.getValue());
			this.model.setSecurityVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectBuilder withCi(SelectedValue option) {
		if (option != null) {
			this.model.setCiValue(option.getValue());
			this.model.setCiVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectBuilder withCd(SelectedValue option) {
		if (option != null) {
			this.model.setCdValue(option.getValue());
			this.model.setCdVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectBuilder withScm(SelectedValue option) {
		if (option != null) {
			this.model.setScmValue(option.getValue());
			this.model.setScmVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectBuilder withRegistry(SelectedValue option) {
		if (option != null) {
			this.model.setRegistryValue(option.getValue());
			this.model.setRegistryVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectBuilder withBuild(SelectedValue option) {
		if (option != null) {
			this.model.setBuildValue(option.getValue());
			this.model.setBuildVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectBuilder withTest(SelectedValue option) {
		if (option != null) {
			this.model.setTestValue(option.getValue());
			this.model.setTestVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectBuilder withNamespace(String namespace) {
		CheckUtils.checkArgumentNotNullOrEmpty(namespace);
		this.model.setNamespace(namespace);
		return this;
	}
	
	public ProjectBuilder withOrganization(String organization) {
		CheckUtils.checkArgumentNotNullOrEmpty(organization);
		this.model.setOrganization(organization);
		return this;
	}
	
	public ProjectBuilder withApiSpec(SpecDefinitions specDefinitions) throws Exception {
		CheckUtils.checkArgumentNotNull(specDefinitions);
		this.specs = specDefinitions;
		return this;
	}
	
	public Project build() {
		Project project = new Project();
		project.setModel(this.model);
		project.setSpecDefinitions(this.specs);
		return project;
	}

}
