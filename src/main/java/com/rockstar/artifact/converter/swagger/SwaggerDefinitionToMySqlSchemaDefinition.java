package com.rockstar.artifact.converter.swagger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rockstar.artifact.codegen.model.MySqlSchemaDefinition;
import com.rockstar.artifact.codegen.model.MySqlTableDefinition;

import io.swagger.models.Model;

@Component
public class SwaggerDefinitionToMySqlSchemaDefinition implements Converter<Map<String, Model>, MySqlSchemaDefinition> {

	@Inject
	private SwaggerModelToMySqlTableDefinition swaggerModelToMySqlTableDefinition;
	
	public MySqlSchemaDefinition convert(Map<String, Model> schemas) {
		MySqlSchemaDefinition mySqlSchemaDefinition = null;
		Collection<MySqlTableDefinition> tableDefinitions = null;
		MySqlTableDefinition table = null;
		
		if (schemas != null) {
			mySqlSchemaDefinition = new MySqlSchemaDefinition();
			tableDefinitions = new ArrayList<MySqlTableDefinition> ();
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
