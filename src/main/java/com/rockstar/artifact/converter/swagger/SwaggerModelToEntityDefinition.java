package com.rockstar.artifact.converter.swagger;

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

import io.swagger.models.Model;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;

@Component
public class SwaggerModelToEntityDefinition implements Converter<Model, EntityDefinition>{

	@Inject private SwaggerPropertyFieldDefinition swaggerPropertyToFieldDefinition;
	
	public EntityDefinition convert(Model schema) {
		EntityDefinition entity = null;
		FieldDefinition field = null;
		RelationshipDefinition oneToOneRelationship = null;
		RelationshipDefinition oneToManyRelationship = null;
		RelationshipDefinition manyToOneRelationship = null;
		Collection<RelationshipDefinition> entityRelationships = null;
		Collection<RelationshipDefinition> childEntityRelationships = null;
		Collection<FieldDefinition> fields = null;
		EntityDefinition childEntity = null;
		Property propertySchema = null;
		String propertyType = null;
		String propertyName = null;
		
		if (schema != null) {
			entity = new EntityDefinition();
			fields = new ArrayList<FieldDefinition> ();
			entityRelationships = new ArrayList<RelationshipDefinition> ();
			if (schema.getProperties() != null && !schema.getProperties().isEmpty()) {
				for (Entry<String, Property> schemaEntry : schema.getProperties().entrySet()) {
					propertyName = schemaEntry.getKey();
					propertySchema = schemaEntry.getValue();
					propertyType = propertySchema.getType();
					
					if (propertyType != null) {
						if (propertySchema instanceof RefProperty) {
							propertyType = ((RefProperty) propertySchema).getType();
						}
						
						/*
						if (propertyType.equalsIgnoreCase("array")) {
							Schema itemsSchema = propertySchema.get
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
						*/
						
						field = this.swaggerPropertyToFieldDefinition.convert(propertySchema);
						field.setName(propertyName);
						/*
						if ( propertySchema.ge() != null && propertySchema.getUniqueItems()) {
							field.setUnique(propertySchema.getUniqueItems());
						}*/
						
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
