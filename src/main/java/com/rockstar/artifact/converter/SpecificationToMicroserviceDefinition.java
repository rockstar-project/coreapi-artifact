package com.rockstar.artifact.converter;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.gatehill.apib.parser.model.AstFormat;
import com.gatehill.apib.parser.model.ParserConfiguration;
import com.gatehill.apib.parser.model.ParsingResult;
import com.gatehill.apib.parser.service.BlueprintParserService;
import com.gatehill.apib.parser.service.SnowcrashBlueprintParserServiceImpl;
import com.reprezen.kaizen.oasparser.OpenApi3Parser;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.rockstar.artifact.codegen.model.MicroserviceDefinition;
import com.rockstar.artifact.converter.apiblueprint.ApiBlueprintASTToApiMicroserviceDefinition;
import com.rockstar.artifact.converter.jsonschema.draft06.JsonSchemaToEventDrivenMicroserviceDefinition;
import com.rockstar.artifact.converter.openapi.OpenApiToRestApiMicroserviceDefinition;
import com.rockstar.artifact.converter.swagger.SwaggerToRestApiMicroserviceDefinition;
import com.rockstar.artifact.model.Specification;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

@Component
public class SpecificationToMicroserviceDefinition implements Converter<Specification, MicroserviceDefinition>  {
	
	@Inject private ApplicationContext applicationContext;
	
	@Inject private OpenApiToRestApiMicroserviceDefinition openApiToRestApiMicroservice;
	@Inject private ApiBlueprintASTToApiMicroserviceDefinition apiblueprintToApiMicroservice;
	@Inject private JsonSchemaToEventDrivenMicroserviceDefinition jsonSchemaToEventDrivenMicroservice;
	@Inject private SwaggerToRestApiMicroserviceDefinition swaggerToRestApiMicroservice;
	
	public MicroserviceDefinition convert(Specification specification) {
		MicroserviceDefinition microserviceDefinition = null;
		String format = null;
		URL location = null;
		String version = null;

		if (specification != null) {
			format = specification.getType();
			version = specification.getVersion();
			
			try {
				location = new URL(specification.getLocation());
			
				if (format != null) {
					if (format.equalsIgnoreCase("jsonschema")) {
						switch (version) {
							case "draft-06":
								microserviceDefinition = this.jsonSchemaToEventDrivenMicroservice.convert(location);
								break;
							default:
								break;
						}
						
					} else if (format.equalsIgnoreCase("openapi")) {
						OpenApi3 openApi = null;
						openApi = new OpenApi3Parser().parse(location, true);
						microserviceDefinition = this.openApiToRestApiMicroservice.convert(openApi);
					} else if (format.equalsIgnoreCase("swagger")) {
						switch (version) {
							case "2":
								Swagger swagger = null;
								swagger = new SwaggerParser().read(location.toString());
								microserviceDefinition = this.swaggerToRestApiMicroservice.convert(swagger);
								break;
							default:
								break;
						}
						
					} else if (format.equalsIgnoreCase("apiblueprint")) {
						BlueprintParserService parser = null;
						ParserConfiguration parserConfig = null;
						ParsingResult<InputStream> parsingResult = null;
						Resource apiblueprintResource = null;
						parser = new SnowcrashBlueprintParserServiceImpl();
						
						parserConfig = new ParserConfiguration();
						parserConfig.setParserHome(new File("/usr/local/bin/snowcrash"));
						apiblueprintResource = this.applicationContext.getResource("url:" + specification.getLocation());
						parsingResult = parser.convertToAstStream(parserConfig, apiblueprintResource.getFile(), AstFormat.YAML);
						microserviceDefinition = this.apiblueprintToApiMicroservice.convert(parsingResult.getAst());
					}
				}
			} catch (Exception exception) {
				
			}
		}
		return microserviceDefinition;
	}

}
