package com.rockstar.artifact.web;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.rockstar.artifact.model.SelectedValue;
import com.rockstar.artifact.model.Specification;

@Relation(value="artifact", collectionRelation="artifacts")
public class ArtifactResource extends ResourceSupport  {
	
	private String namespace;
	private String organization;
	private String type;
	private SelectedValue language;
	private SelectedValue framework;
	private Specification specification;
	private SelectedValue datastore;
	private SelectedValue http;
	private SelectedValue messaging;
	private SelectedValue discovery;
	private SelectedValue tracing;
	private SelectedValue monitoring;
	private SelectedValue security;
	private SelectedValue ci;
	private SelectedValue cd;
	private SelectedValue scm;
    private SelectedValue registry;
    private SelectedValue build;
    private SelectedValue test;
	
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

	@NotNull
	@Valid
	public SelectedValue getLanguage() {
		return language;
	}

	public void setLanguage(SelectedValue language) {
		this.language = language;
	}

	@NotNull
	@Valid
	public SelectedValue getFramework() {
		return framework;
	}

	public void setFramework(SelectedValue framework) {
		this.framework = framework;
	}
	
	@NotNull
	@Valid
	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	@NotEmpty
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	public String getSlug() {
		return String.format("%s-%s-%s", this.type, this.language.getValue(), this.framework.getValue());
	}

	public SelectedValue getDatastore() {
		return datastore;
	}

	public void setDatastore(SelectedValue datastore) {
		this.datastore = datastore;
	}

	public SelectedValue getHttp() {
		return http;
	}

	public void setHttp(SelectedValue http) {
		this.http = http;
	}

	public SelectedValue getMessaging() {
		return messaging;
	}

	public void setMessaging(SelectedValue messaging) {
		this.messaging = messaging;
	}

	public SelectedValue getDiscovery() {
		return discovery;
	}

	public void setDiscovery(SelectedValue discovery) {
		this.discovery = discovery;
	}

	public SelectedValue getTracing() {
		return tracing;
	}

	public void setTracing(SelectedValue tracing) {
		this.tracing = tracing;
	}

	public SelectedValue getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(SelectedValue monitoring) {
		this.monitoring = monitoring;
	}

	public SelectedValue getSecurity() {
		return security;
	}

	public void setSecurity(SelectedValue security) {
		this.security = security;
	}

	public SelectedValue getCi() {
		return ci;
	}

	public void setCi(SelectedValue ci) {
		this.ci = ci;
	}

	public SelectedValue getCd() {
		return cd;
	}

	public void setCd(SelectedValue cd) {
		this.cd = cd;
	}

	public SelectedValue getScm() {
		return scm;
	}

	public void setScm(SelectedValue scm) {
		this.scm = scm;
	}

	public SelectedValue getRegistry() {
		return registry;
	}

	public void setRegistry(SelectedValue registry) {
		this.registry = registry;
	}

	public SelectedValue getBuild() {
		return build;
	}

	public void setBuild(SelectedValue build) {
		this.build = build;
	}

	public SelectedValue getTest() {
		return test;
	}

	public void setTest(SelectedValue test) {
		this.test = test;
	}
	
}
