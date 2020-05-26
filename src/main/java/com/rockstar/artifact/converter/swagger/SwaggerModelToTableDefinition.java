package com.rockstar.artifact.converter.swagger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rockstar.artifact.codegen.model.FieldDefinition;
import com.rockstar.artifact.codegen.model.TableDefinition;

import io.swagger.models.Model;
import io.swagger.models.properties.Property;

@Component
public class SwaggerModelToTableDefinition implements Converter<Model, TableDefinition> {

	@Inject
	private SwaggerPropertyToFieldDefinition swaggerPropertyToFieldDefinition;
	
	public TableDefinition convert(Model schema) {
		TableDefinition mysqlTableDefinition = null;
		Collection<FieldDefinition> columns = null;
		FieldDefinition mysqlColumnDefinition = null;
		Property propertySchema = null;
		String propertyName = null;
		
		if (schema != null) {
			mysqlTableDefinition = new TableDefinition();
			
			if (schema.getProperties() != null && !schema.getProperties().isEmpty()) {
				columns = new ArrayList<FieldDefinition> ();
				for (Entry<String, Property> schemaEntry : schema.getProperties().entrySet()) {
					propertyName = schemaEntry.getKey();
					propertySchema = schemaEntry.getValue();
					mysqlColumnDefinition = this.swaggerPropertyToFieldDefinition.convert(propertySchema);
					mysqlColumnDefinition.setName(propertyName);
					columns.add(mysqlColumnDefinition);
				}
			}
			mysqlTableDefinition.setColumns(columns);
		}
		
		return mysqlTableDefinition;
	}

}
