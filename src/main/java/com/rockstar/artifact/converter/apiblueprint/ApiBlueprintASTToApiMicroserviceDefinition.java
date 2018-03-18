package com.rockstar.artifact.converter.apiblueprint;

import java.io.InputStream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rockstar.artifact.codegen.model.MicroserviceDefinition;

@Component
public class ApiBlueprintASTToApiMicroserviceDefinition implements Converter<InputStream, MicroserviceDefinition>  {
	
	public MicroserviceDefinition convert(InputStream astStream) {
		MicroserviceDefinition microserviceDefinition = null;
		
		if (astStream != null) {
			microserviceDefinition = new MicroserviceDefinition();
			
		}
		return microserviceDefinition;
	}

}
