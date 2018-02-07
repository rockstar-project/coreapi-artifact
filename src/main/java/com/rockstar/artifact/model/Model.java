package com.rockstar.artifact.model;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.reprezen.kaizen.oasparser.jsonoverlay.Reference;
import com.reprezen.kaizen.oasparser.model3.Schema;

public class Model {
	
	private String packageName;
	private String version;
	private String contact;
	private String type;
	private String namespace;
	private String organization;
	private Schema schema;
	private String classname;
	private String languageValue;
	private String languageVersion;
	private String frameworkValue;
	private String frameworkVersion;
	private String datastoreValue;
	private String datastoreVersion;
	private String httpValue;
	private String httpVersion;
	private String discoveryValue;
	private String discoveryVersion;
	private String messagingValue;
	private String messagingVersion;
	private String tracingValue;
	private String tracingVersion;
	private String monitoringValue;
	private String monitoringVersion;
	private String securityValue;
	private String securityVersion;
	private String ciValue;
	private String ciVersion;
	private String cdValue;
	private String cdVersion;
	private String scmValue;
	private String scmVersion;
	private String registryValue;
	private String registryVersion;
	private String buildValue;
	private String buildVersion;
	private String testValue;
	private String testVersion;
	
	public Model() {
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassname() {
		return this.classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public Schema getSchema() {
		return this.schema;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	public Set<Map.Entry<String, Schema>> getFields() {
		Set<Map.Entry<String,Schema>> entrySet = new HashSet<Map.Entry<String, Schema>> ();
		Map<String, Schema> properties = this.schema.getProperties();
		if (properties != null && !properties.isEmpty()) {
			
			for (Entry<String, Schema> current : properties.entrySet()) {
				
				if (this.schema.getPropertyReference(current.getKey()) == null) {
					entrySet.add(current);
				}
			}
		}
		return entrySet;
	}
	
	public Set<Map.Entry<String, Schema>> getRequiredFields() {
		Collection<String> requiredFields = null;
		Set<Map.Entry<String,Schema>> entrySet = new HashSet<Map.Entry<String, Schema>> ();
		Map<String, Schema> properties = this.schema.getProperties();
		if (properties != null && !properties.isEmpty()) {
			requiredFields = this.schema.getRequiredFields();
			
			for (Entry<String, Schema> current : properties.entrySet()) {
				
				if (requiredFields != null && requiredFields.contains(current.getKey())) {
					
						entrySet.add(current);
					
				}
			}
		}
		return entrySet;
	}
	
	public Set<Map.Entry<String, Schema>> getNonRequiredFields() {
		Collection<String> requiredFields = null;
		Set<Map.Entry<String,Schema>> entrySet = new HashSet<Map.Entry<String, Schema>> ();
		Map<String, Schema> properties = this.schema.getProperties();
		if (properties != null && !properties.isEmpty()) {
			requiredFields = this.schema.getRequiredFields();
			
			for (Entry<String, Schema> current : properties.entrySet()) {
				
				if (requiredFields != null && !requiredFields.contains(current.getKey())) {
					
						entrySet.add(current);
					
				}
			}
		}
		return entrySet;
	}
	
	public Set<Map.Entry<String, Reference>> getReferenceFields() {
		Map<String, Reference> referenceFields = new HashMap<String, Reference>();
		Set<Map.Entry<String,Reference>> entrySet = new HashSet<Map.Entry<String, Reference>> ();
		Map<String, Schema> properties = this.schema.getProperties();
		if (properties != null && !properties.isEmpty()) {
			
			for (Entry<String, Schema> current : properties.entrySet()) {
				if (this.schema.getPropertyReference(current.getKey()) != null) {
					referenceFields.put(current.getKey(), this.schema.getPropertyReference(current.getKey()));
				}
			}
			for (Entry<String, Reference> current : referenceFields.entrySet()) {
				entrySet.add(current);
			}
		}
		return entrySet;
	}
	
	public Set<Map.Entry<String,Schema>> getEnumfields() {
		Set<Map.Entry<String,Schema>> entrySet = new HashSet<Map.Entry<String, Schema>> ();
		for (Entry<String, Schema> current : this.getFields()) {
			Schema property = current.getValue();
			if (property.hasEnums()) {
				entrySet.add(current);
			}
		}
		
		return entrySet;
	}
	
	public Set<Map.Entry<String,Schema>> getUniqueItems() {
		Set<Map.Entry<String,Schema>> entrySet = new HashSet<Map.Entry<String, Schema>> ();
		for (Entry<String, Schema> current : this.getFields()) {
			Schema property = current.getValue();
			if (property.isUniqueItems()) {
				entrySet.add(current);
			}
		}
		
		return entrySet;
	}

	public String getLanguageValue() {
		return languageValue;
	}

	public void setLanguageValue(String languageValue) {
		this.languageValue = languageValue;
	}

	public String getLanguageVersion() {
		return languageVersion;
	}

	public void setLanguageVersion(String languageVersion) {
		this.languageVersion = languageVersion;
	}

	public String getFrameworkValue() {
		return frameworkValue;
	}

	public void setFrameworkValue(String frameworkValue) {
		this.frameworkValue = frameworkValue;
	}

	public String getFrameworkVersion() {
		return frameworkVersion;
	}

	public void setFrameworkVersion(String frameworkVersion) {
		this.frameworkVersion = frameworkVersion;
	}

	public String getDatastoreValue() {
		return datastoreValue;
	}

	public void setDatastoreValue(String datastoreValue) {
		this.datastoreValue = datastoreValue;
	}

	public String getDatastoreVersion() {
		return datastoreVersion;
	}

	public void setDatastoreVersion(String datastoreVersion) {
		this.datastoreVersion = datastoreVersion;
	}

	public String getHttpValue() {
		return httpValue;
	}

	public void setHttpValue(String httpValue) {
		this.httpValue = httpValue;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	public String getDiscoveryValue() {
		return discoveryValue;
	}

	public void setDiscoveryValue(String discoveryValue) {
		this.discoveryValue = discoveryValue;
	}

	public String getDiscoveryVersion() {
		return discoveryVersion;
	}

	public void setDiscoveryVersion(String discoveryVersion) {
		this.discoveryVersion = discoveryVersion;
	}

	public String getMessagingValue() {
		return messagingValue;
	}

	public void setMessagingValue(String messagingValue) {
		this.messagingValue = messagingValue;
	}

	public String getMessagingVersion() {
		return messagingVersion;
	}

	public void setMessagingVersion(String messagingVersion) {
		this.messagingVersion = messagingVersion;
	}

	public String getTracingValue() {
		return tracingValue;
	}

	public void setTracingValue(String tracingValue) {
		this.tracingValue = tracingValue;
	}

	public String getTracingVersion() {
		return tracingVersion;
	}

	public void setTracingVersion(String tracingVersion) {
		this.tracingVersion = tracingVersion;
	}

	public String getMonitoringValue() {
		return monitoringValue;
	}

	public void setMonitoringValue(String monitoringValue) {
		this.monitoringValue = monitoringValue;
	}

	public String getMonitoringVersion() {
		return monitoringVersion;
	}

	public void setMonitoringVersion(String monitoringVersion) {
		this.monitoringVersion = monitoringVersion;
	}

	public String getSecurityValue() {
		return securityValue;
	}

	public void setSecurityValue(String securityValue) {
		this.securityValue = securityValue;
	}

	public String getSecurityVersion() {
		return securityVersion;
	}

	public void setSecurityVersion(String securityVersion) {
		this.securityVersion = securityVersion;
	}

	public String getCiValue() {
		return ciValue;
	}

	public void setCiValue(String ciValue) {
		this.ciValue = ciValue;
	}

	public String getCiVersion() {
		return ciVersion;
	}

	public void setCiVersion(String ciVersion) {
		this.ciVersion = ciVersion;
	}

	public String getCdValue() {
		return cdValue;
	}

	public void setCdValue(String cdValue) {
		this.cdValue = cdValue;
	}

	public String getCdVersion() {
		return cdVersion;
	}

	public void setCdVersion(String cdVersion) {
		this.cdVersion = cdVersion;
	}

	public String getScmValue() {
		return scmValue;
	}

	public void setScmValue(String scmValue) {
		this.scmValue = scmValue;
	}

	public String getScmVersion() {
		return scmVersion;
	}

	public void setScmVersion(String scmVersion) {
		this.scmVersion = scmVersion;
	}

	public String getRegistryValue() {
		return registryValue;
	}

	public void setRegistryValue(String registryValue) {
		this.registryValue = registryValue;
	}

	public String getRegistryVersion() {
		return registryVersion;
	}

	public void setRegistryVersion(String registryVersion) {
		this.registryVersion = registryVersion;
	}

	public String getBuildValue() {
		return buildValue;
	}

	public void setBuildValue(String buildValue) {
		this.buildValue = buildValue;
	}

	public String getBuildVersion() {
		return buildVersion;
	}

	public void setBuildVersion(String buildVersion) {
		this.buildVersion = buildVersion;
	}

	public String getTestValue() {
		return testValue;
	}

	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}

	public String getTestVersion() {
		return testVersion;
	}

	public void setTestVersion(String testVersion) {
		this.testVersion = testVersion;
	}
	

}
