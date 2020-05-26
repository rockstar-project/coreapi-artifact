package com.rockstar.artifact.converter.openapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.DatabaseSchemaDefinition;
import com.rockstar.artifact.codegen.model.TableDefinition;

@Component
public class OpenApiSchemaToDatabaseSchemaDefinition implements Converter<Map<String, Schema>, DatabaseSchemaDefinition> {

	@Inject
	private OpenApiSchemaToTableDefinition openApiSchemaToTable;
	
	public DatabaseSchemaDefinition convert(Map<String, Schema> schemas) {
		DatabaseSchemaDefinition mySqlSchemaDefinition = null;
		Collection<TableDefinition> tableDefinitions = null;
		TableDefinition table = null;
		
		if (schemas != null) {
			mySqlSchemaDefinition = new DatabaseSchemaDefinition();
			tableDefinitions = new ArrayList<TableDefinition> ();
			for (Entry<String, Schema> schemaEntry : schemas.entrySet()) {
				table = this.openApiSchemaToTable.convert(schemaEntry.getValue());
				table.setName(schemaEntry.getKey());
				tableDefinitions.add(table);
			}
			mySqlSchemaDefinition.setTables(tableDefinitions);
		}
		return mySqlSchemaDefinition;
	}

}
