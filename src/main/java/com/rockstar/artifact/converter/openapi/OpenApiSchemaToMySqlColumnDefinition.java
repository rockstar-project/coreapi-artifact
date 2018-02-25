package com.rockstar.artifact.converter.openapi;

import javax.inject.Inject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.Schema;
import com.rockstar.artifact.codegen.model.MySqlColumnDefinition;

@Component
public class OpenApiSchemaToMySqlColumnDefinition implements Converter<Schema, MySqlColumnDefinition> {

	@Inject private OpenApiTypeToMySqlType openApiTypeToMySqlType;
	
	public MySqlColumnDefinition convert(Schema schema) {
		MySqlColumnDefinition mysqlColumnDefinition = null;
		
		if (schema != null) {
			mysqlColumnDefinition = new MySqlColumnDefinition();
			mysqlColumnDefinition.setDefaultValue(schema.getDefault());
			mysqlColumnDefinition.setType(this.openApiTypeToMySqlType.convert(schema));
			mysqlColumnDefinition.setMaximumLength(schema.getMaxLength());
			mysqlColumnDefinition.setNullable(schema.getNullable());
		}
		return mysqlColumnDefinition;
	}

}
