package com.rockstar.artifact.converter.openapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.FieldDefinition;
import com.rockstar.artifact.codegen.model.TableDefinition;

@Component
public class OpenApiSchemaToTableDefinition implements Converter<Schema, TableDefinition> {

	@Inject
	private OpenApiSchemaToFieldDefinition openApiSchemaToFieldDefinition;
	
	public TableDefinition convert(Schema schema) {
		TableDefinition mysqlTableDefinition = null;
		Collection<FieldDefinition> columns = null;
		FieldDefinition columnDefinition = null;
		Schema propertySchema = null;
		String propertyName = null;
		
		if (schema != null) {
			mysqlTableDefinition = new TableDefinition();
			
			if (schema.getProperties() != null && !schema.getProperties().isEmpty()) {
				columns = new ArrayList<FieldDefinition> ();
				for (Entry<String, Schema> schemaEntry : schema.getProperties().entrySet()) {
					propertyName = schemaEntry.getKey();
					propertySchema = schemaEntry.getValue();
					columnDefinition = this.openApiSchemaToFieldDefinition.convert(propertySchema);
					columnDefinition.setName(propertyName);
					columns.add(columnDefinition);
				}
			}
			mysqlTableDefinition.setColumns(columns);
		}
		
		return mysqlTableDefinition;
	}

}
