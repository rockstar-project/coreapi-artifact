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

@Component
public class OpenApiSchemaToEntityDefinition implements Converter<Schema, EntityDefinition>{

	@Inject private OpenApiSchemaToFieldDefinition openApiSchemaToFieldDefinition;
	
	public EntityDefinition convert(Schema schema) {
		EntityDefinition entity = null;
		FieldDefinition field = null;
		Collection<RelationshipDefinition> relationships = null;
		Collection<FieldDefinition> fields = null;
		RelationshipDefinition relationshipDefinition = null;
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
					
					switch (propertyType) {
						case "object":
							// TODO
							break;
						case "array":
							Schema itemsSchema = propertySchema.getItemsSchema();
							if (itemsSchema != null) {
								relationshipDefinition = new RelationshipDefinition();
								relationshipDefinition.setType(RelationshipType.OneToMany);
								relationshipDefinition.setRelatedEntity(this.convert(itemsSchema));
								relationships.add(relationshipDefinition);
							}
							break;
						default:
							field = this.openApiSchemaToFieldDefinition.convert(propertySchema);
							field.setName(propertyName);
							fields.add(field);
							break;
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
