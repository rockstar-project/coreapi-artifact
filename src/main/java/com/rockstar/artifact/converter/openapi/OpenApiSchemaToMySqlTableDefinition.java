package com.rockstar.artifact.converter.openapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.MySqlColumnDefinition;
import com.rockstar.artifact.codegen.model.MySqlTableDefinition;

@Component
public class OpenApiSchemaToMySqlTableDefinition implements Converter<Schema, MySqlTableDefinition> {

	@Inject
	private OpenApiSchemaToMySqlColumnDefinition openApiSchemaToMySqlColumn;
	
	public MySqlTableDefinition convert(Schema schema) {
		MySqlTableDefinition mysqlTableDefinition = null;
		Collection<MySqlColumnDefinition> columns = null;
		MySqlColumnDefinition mysqlColumnDefinition = null;
		Schema propertySchema = null;
		String propertyName = null;
		
		if (schema != null) {
			mysqlTableDefinition = new MySqlTableDefinition();
			
			if (schema.getProperties() != null && !schema.getProperties().isEmpty()) {
				columns = new ArrayList<MySqlColumnDefinition> ();
				for (Entry<String, Schema> schemaEntry : schema.getProperties().entrySet()) {
					propertyName = schemaEntry.getKey();
					propertySchema = schemaEntry.getValue();
					mysqlColumnDefinition = this.openApiSchemaToMySqlColumn.convert(propertySchema);
					mysqlColumnDefinition.setName(propertyName);
					columns.add(mysqlColumnDefinition);
				}
			}
			mysqlTableDefinition.setColumns(columns);
		}
		
		return mysqlTableDefinition;
	}

}
