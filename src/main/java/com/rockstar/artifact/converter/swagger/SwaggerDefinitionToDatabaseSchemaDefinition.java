package com.rockstar.artifact.converter.swagger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rockstar.artifact.codegen.model.DatabaseSchemaDefinition;
import com.rockstar.artifact.codegen.model.TableDefinition;

import io.swagger.models.Model;

@Component
public class SwaggerDefinitionToDatabaseSchemaDefinition implements Converter<Map<String, Model>, DatabaseSchemaDefinition> {

	@Inject
	private SwaggerModelToTableDefinition swaggerModelToMySqlTableDefinition;
	
	public DatabaseSchemaDefinition convert(Map<String, Model> schemas) {
		DatabaseSchemaDefinition mySqlSchemaDefinition = null;
		Collection<TableDefinition> tableDefinitions = null;
		TableDefinition table = null;
		
		if (schemas != null) {
			mySqlSchemaDefinition = new DatabaseSchemaDefinition();
			tableDefinitions = new ArrayList<TableDefinition> ();
			for (Entry<String, Model> schemaEntry : schemas.entrySet()) {
				table = this.swaggerModelToMySqlTableDefinition.convert(schemaEntry.getValue());
				table.setName(schemaEntry.getKey());
				tableDefinitions.add(table);
			}
			mySqlSchemaDefinition.setTables(tableDefinitions);
		}
		return mySqlSchemaDefinition;
	}

}
