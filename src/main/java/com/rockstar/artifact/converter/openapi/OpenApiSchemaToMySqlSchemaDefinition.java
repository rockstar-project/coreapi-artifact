package com.rockstar.artifact.converter.openapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.MySqlSchemaDefinition;
import com.rockstar.artifact.codegen.model.MySqlTableDefinition;

@Component
public class OpenApiSchemaToMySqlSchemaDefinition implements Converter<Map<String, Schema>, MySqlSchemaDefinition> {

	@Inject
	private OpenApiSchemaToMySqlTableDefinition openApiSchemaToMySqlTable;
	
	public MySqlSchemaDefinition convert(Map<String, Schema> schemas) {
		MySqlSchemaDefinition mySqlSchemaDefinition = null;
		Collection<MySqlTableDefinition> tableDefinitions = null;
		MySqlTableDefinition table = null;
		
		if (schemas != null) {
			mySqlSchemaDefinition = new MySqlSchemaDefinition();
			tableDefinitions = new ArrayList<MySqlTableDefinition> ();
			for (Entry<String, Schema> schemaEntry : schemas.entrySet()) {
				table = this.openApiSchemaToMySqlTable.convert(schemaEntry.getValue());
				table.setName(schemaEntry.getKey());
				tableDefinitions.add(table);
			}
			mySqlSchemaDefinition.setTables(tableDefinitions);
		}
		return mySqlSchemaDefinition;
	}

}
