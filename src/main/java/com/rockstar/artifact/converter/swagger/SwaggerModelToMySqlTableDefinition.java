package com.rockstar.artifact.converter.swagger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rockstar.artifact.codegen.model.MySqlColumnDefinition;
import com.rockstar.artifact.codegen.model.MySqlTableDefinition;

import io.swagger.models.Model;
import io.swagger.models.properties.Property;

@Component
public class SwaggerModelToMySqlTableDefinition implements Converter<Model, MySqlTableDefinition> {

	@Inject
	private SwaggerPropertyToMySqlColumnDefinition swaggerPropertyToMySqlColumnDefinition;
	
	public MySqlTableDefinition convert(Model schema) {
		MySqlTableDefinition mysqlTableDefinition = null;
		Collection<MySqlColumnDefinition> columns = null;
		MySqlColumnDefinition mysqlColumnDefinition = null;
		Property propertySchema = null;
		String propertyName = null;
		
		if (schema != null) {
			mysqlTableDefinition = new MySqlTableDefinition();
			
			if (schema.getProperties() != null && !schema.getProperties().isEmpty()) {
				columns = new ArrayList<MySqlColumnDefinition> ();
				for (Entry<String, Property> schemaEntry : schema.getProperties().entrySet()) {
					propertyName = schemaEntry.getKey();
					propertySchema = schemaEntry.getValue();
					mysqlColumnDefinition = this.swaggerPropertyToMySqlColumnDefinition.convert(propertySchema);
					mysqlColumnDefinition.setName(propertyName);
					columns.add(mysqlColumnDefinition);
				}
			}
			mysqlTableDefinition.setColumns(columns);
		}
		
		return mysqlTableDefinition;
	}

}
