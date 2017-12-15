package com.ibmcloud.kickster.web;

import java.util.Map;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value="artifact", collectionRelation="artifacts")
public class ArtifactResource extends ResourceSupport  {
	
	private String namespace;
	private String organization;
	private String type;
	private String language;
	private String framework;
	private String specification;
	private Map<String, String> options;
	
	public ArtifactResource() {
	}

	@NotEmpty
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	@NotEmpty
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@NotEmpty
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@NotEmpty
	public String getFramework() {
		return framework;
	}

	public void setFramework(String framework) {
		this.framework = framework;
	}

	@NotNull
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@NotEmpty
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}
	
	public String getSlug() {
		return String.format("%s-%s", this.language, this.framework);
	}
	
}
