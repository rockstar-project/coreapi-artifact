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
	private Map<String, String> options;
	
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

	public Map<String, String> getOptions() {
		return this.options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
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
	
}
