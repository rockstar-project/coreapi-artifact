package com.rockstar.artifact.converter.swagger;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rockstar.artifact.codegen.model.MySqlColumnDefinition;

import io.swagger.models.properties.Property;

@Component
public class SwaggerPropertyToMySqlColumnDefinition implements Converter<Property, MySqlColumnDefinition> {

	@Inject private SwaggerPropertyTypeToMySqlType swaggerPropertyTypeToMySqlType;
	
	public MySqlColumnDefinition convert(Property schema) {
		MySqlColumnDefinition mysqlColumnDefinition = null;
		
		if (schema != null) {
			mysqlColumnDefinition = new MySqlColumnDefinition();
			mysqlColumnDefinition.setName(schema.getName());
			mysqlColumnDefinition.setType(this.swaggerPropertyTypeToMySqlType.convert(schema));
			//mysqlColumnDefinition.setMaximumLength(schema.());
			mysqlColumnDefinition.setNullable(schema.getAllowEmptyValue());
		}
		return mysqlColumnDefinition;
	}

}
