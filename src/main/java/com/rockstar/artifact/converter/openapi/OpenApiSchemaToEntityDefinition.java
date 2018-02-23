package com.rockstar.artifact.converter.openapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.EntityDefinition;
import com.rockstar.artifact.codegen.model.FieldDefinition;
import com.rockstar.artifact.codegen.model.RelationshipDefinition;
import com.rockstar.artifact.codegen.model.RelationshipType;

@Component
public class OpenApiSchemaToEntityDefinition implements Converter<Schema, EntityDefinition>{

	@Inject private OpenApiSchemaToFieldDefinition openApiSchemaToFieldDefinition;
	
	public EntityDefinition convert(Schema schema) {
		EntityDefinition entity = null;
		FieldDefinition field = null;
		Collection<RelationshipDefinition> relationships = null;
		Collection<FieldDefinition> fields = null;
		Schema propertySchema = null;
		String propertyType = null;
		String propertyName = null;
		
		if (schema != null) {
			entity = new EntityDefinition();
			fields = new ArrayList<FieldDefinition> ();
			relationships = new ArrayList<RelationshipDefinition> ();
			if (schema.getProperties() != null && !schema.getProperties().isEmpty()) {
				for (Entry<String, Schema> schemaEntry : schema.getProperties().entrySet()) {
					propertyName = schemaEntry.getKey();
					propertySchema = schemaEntry.getValue();
					propertyType = propertySchema.getType();
					
					if (propertyType != null) {
						if (propertyType.equalsIgnoreCase("array")) {
							Schema itemsSchema = propertySchema.getItemsSchema();
							if (itemsSchema != null) {
								RelationshipDefinition oneToManyRelationship = null;
								EntityDefinition childEntity = new EntityDefinition();
								childEntity.setName(StringUtils.uncapitalize(propertyName));
								
								oneToManyRelationship = new RelationshipDefinition();
								oneToManyRelationship.setType(RelationshipType.OneToMany);
								oneToManyRelationship.setChild(childEntity);
								relationships.add(oneToManyRelationship);
							}
						}
						
						field = this.openApiSchemaToFieldDefinition.convert(propertySchema);
						field.setName(propertyName);
						if ( propertySchema.getUniqueItems() != null && propertySchema.getUniqueItems()) {
							field.setUnique(propertySchema.getUniqueItems());
						}
						
						fields.add(field);
					} else {
						EntityDefinition childEntity = null;
						RelationshipDefinition oneToOneRelationship = null;
						
						childEntity = new EntityDefinition();
						childEntity.setName(propertyName);
						
						oneToOneRelationship = new RelationshipDefinition();
						oneToOneRelationship.setChild(childEntity);
						oneToOneRelationship.setType(RelationshipType.OneToOne);
						relationships.add(oneToOneRelationship);
					}
				}
				
			}
			if (!fields.isEmpty()) {
				entity.setFields(fields);
			}
			if (!relationships.isEmpty()) {
				entity.setRelationships(relationships);
			}
		}
		return entity;
	}

}
