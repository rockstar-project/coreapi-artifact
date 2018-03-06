package com.rockstar.artifact.converter.openapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.EntityDefinition;
import com.rockstar.artifact.codegen.model.FieldDefinition;
import com.rockstar.artifact.codegen.model.RelationshipDefinition;
import com.rockstar.artifact.codegen.model.RelationshipType;
import com.rockstar.artifact.util.WordUtils;

@Component
public class OpenApiSchemaToEntityDefinition implements Converter<Schema, EntityDefinition>{

	@Inject private OpenApiSchemaToFieldDefinition openApiSchemaToFieldDefinition;
	
	public EntityDefinition convert(Schema schema) {
		EntityDefinition entity = null;
		FieldDefinition field = null;
		RelationshipDefinition oneToOneRelationship = null;
		RelationshipDefinition oneToManyRelationship = null;
		RelationshipDefinition manyToOneRelationship = null;
		Collection<RelationshipDefinition> entityRelationships = null;
		Collection<RelationshipDefinition> childEntityRelationships = null;
		Collection<FieldDefinition> fields = null;
		EntityDefinition childEntity = null;
		Schema propertySchema = null;
		String propertyType = null;
		String propertyName = null;
		
		if (schema != null) {
			entity = new EntityDefinition();
			fields = new ArrayList<FieldDefinition> ();
			entityRelationships = new ArrayList<RelationshipDefinition> ();
			if (schema.getProperties() != null && !schema.getProperties().isEmpty()) {
				for (Entry<String, Schema> schemaEntry : schema.getProperties().entrySet()) {
					propertyName = schemaEntry.getKey();
					propertySchema = schemaEntry.getValue();
					propertyType = propertySchema.getType();
					
					if (propertyType != null) {
						if (propertyType.equalsIgnoreCase("array")) {
							Schema itemsSchema = propertySchema.getItemsSchema();
							if (itemsSchema != null) {
								
								childEntity = this.convert(itemsSchema);
								childEntity.setName(WordUtils.uncapitalizeSingular(propertyName));
								
								oneToManyRelationship = new RelationshipDefinition();
								oneToManyRelationship.setType(RelationshipType.OneToMany);
								oneToManyRelationship.setChild(childEntity);
								entityRelationships.add(oneToManyRelationship);
								
								manyToOneRelationship = new RelationshipDefinition();
								manyToOneRelationship.setType(RelationshipType.ManyToOne);
								
								childEntityRelationships = new ArrayList<RelationshipDefinition> ();
								childEntityRelationships.add(manyToOneRelationship);
								childEntity.setRelationships(childEntityRelationships);
							}
						}
						
						field = this.openApiSchemaToFieldDefinition.convert(propertySchema);
						field.setName(propertyName);
						if ( propertySchema.getUniqueItems() != null && propertySchema.getUniqueItems()) {
							field.setUnique(propertySchema.getUniqueItems());
						}
						
						fields.add(field);
					} else {
				
						childEntity = new EntityDefinition();
						childEntity.setName(propertyName);
						
						oneToOneRelationship = new RelationshipDefinition();
						oneToOneRelationship.setChild(childEntity);
						oneToOneRelationship.setType(RelationshipType.OneToOne);
						entityRelationships.add(oneToOneRelationship);
					}
				}
				
			}
			if (!fields.isEmpty()) {
				entity.setFields(fields);
			}
			if (!entityRelationships.isEmpty()) {
				entity.setRelationships(entityRelationships);
			}
		}
		return entity;
	}

}
