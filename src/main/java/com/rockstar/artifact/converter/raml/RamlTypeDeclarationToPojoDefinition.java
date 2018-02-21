package com.rockstar.artifact.converter.raml;

import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.springframework.core.convert.converter.Converter;

import com.rockstar.artifact.codegen.model.PojoDefinition;

public class RamlTypeDeclarationToPojoDefinition implements Converter<TypeDeclaration, PojoDefinition>{

	public PojoDefinition convert(TypeDeclaration schema) {
		PojoDefinition pojoDefinition = null;
		
		if (schema != null) {
			pojoDefinition = new PojoDefinition();
			pojoDefinition.setName(schema.name());
			// TODO - parse raml object & convert to pojo definition
		}
		return pojoDefinition;
	}

}
