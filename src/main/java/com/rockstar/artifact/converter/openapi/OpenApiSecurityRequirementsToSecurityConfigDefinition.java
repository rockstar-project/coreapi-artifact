package com.rockstar.artifact.converter.openapi;

import java.util.Collection;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.model3.SecurityRequirement;
import com.rockstar.artifact.codegen.model.SecurityDefinition;

@Component
public class OpenApiSecurityRequirementsToSecurityConfigDefinition implements Converter<Collection<SecurityRequirement>, SecurityDefinition> {

	public SecurityDefinition convert(Collection<SecurityRequirement> securityRequirements) {
		// TODO
		return new SecurityDefinition();
	}

}
