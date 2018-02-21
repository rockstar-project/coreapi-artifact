package com.rockstar.artifact;

import java.net.URI;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.util.StringUtils;
import org.trimou.engine.MustacheEngine;
import org.trimou.engine.MustacheEngineBuilder;
import org.trimou.engine.config.EngineConfigurationKey;
import org.trimou.engine.locator.ClassPathTemplateLocator;
import org.trimou.handlebars.HelpersBuilder;
import org.trimou.handlebars.SimpleHelpers;

import com.reprezen.kaizen.oasparser.OpenApi3Parser;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.reprezen.kaizen.oasparser.val.ValidationResults.ValidationItem;
import com.rockstar.artifact.codegen.model.ArtifactDefinition;
import com.rockstar.artifact.converter.openapi.OpenApiToArtifactDefinition;
import com.rockstar.artifact.model.InvalidSchemaException;
import com.rockstar.artifact.model.Model;
import com.rockstar.artifact.util.CheckUtils;
import com.rockstar.artifact.util.WordUtils;

public class TrimouTemplateTest {
	
	public static MustacheEngine templateEngine() throws Exception {
		MustacheEngineBuilder builder = MustacheEngineBuilder.newBuilder()
				.setProperty(EngineConfigurationKey.SKIP_VALUE_ESCAPING, true)
	        	.setProperty(EngineConfigurationKey.PRECOMPILE_ALL_TEMPLATES, true)
	        	.setProperty(EngineConfigurationKey.DEBUG_MODE, true)
	        	.registerHelpers(
                        HelpersBuilder.empty()
                                .addIsNull()
                                .addIsNotNull()
                                .addSwitch()
                                .addIsEqual()
                                .addIsNotEqual()
                                .addInclude()
                                .addJoin()
                                .build())
	        	.registerHelper("capitalizePlural", SimpleHelpers.execute(
	                    (o, c) -> {
	                        o.append(WordUtils.capitalizePlural(o.getParameters().get(0).toString()));
	                    })
	                )
	        	.registerHelper("capitalizeSingular", SimpleHelpers.execute(
	                    (o, c) -> {
	                        o.append(WordUtils.capitalizeSingular(o.getParameters().get(0).toString()));
	                    })
	                )
	        	.registerHelper("capitalize", SimpleHelpers.execute(
	                    (o, c) -> {
	                    	CheckUtils.checkArgumentsNotNull(o, o.getParameters(), o.getParameters().get(0));
	                        o.append(StringUtils.capitalize(o.getParameters().get(0).toString()));
	                    })
	                )
	        	.registerHelper("pluralize", SimpleHelpers.execute(
	                    (o, c) -> {
	                        o.append(WordUtils.uncapitalizePlural(o.getParameters().get(0).toString()));
	                    })
	                )
	        	.registerHelper("uppercase", SimpleHelpers.execute(
	                    (o, c) -> {
	                        o.append(org.apache.commons.lang3.StringUtils.upperCase(o.getParameters().get(0).toString()));
	                    })
	                )
	        	.registerHelper("capitalizeCamelcase", SimpleHelpers.execute(
	                    (o, c) -> {
	                        o.append(StringUtils.capitalize(WordUtils.camelcase(o.getParameters().get(0).toString())));
	                    })
	                )
	        	.registerHelper("camelcaseToUpperSnakecase", SimpleHelpers.execute(
	        			(o, c) -> {
	                        o.append(WordUtils.camelCaseToUpperSnakeCase(o.getParameters().get(0).toString()));
	                    })
	                )
	        	.registerHelper("snakecaseToCamelcase", SimpleHelpers.execute(
	        			(o, c) -> {
	                        o.append(WordUtils.snakecaseToCamelcase(o.getParameters().get(0).toString()));
	                    })
	                )
	        	.registerHelper("camelcase", SimpleHelpers.execute(
	                    (o, c) -> {
	                        o.append(WordUtils.camelcase(o.getParameters().get(0).toString()));
	                    })
	                )
	        	.registerHelper("pathCamelcase", SimpleHelpers.execute(
	        			(o, c) -> {
	                        o.append(WordUtils.fromPathCamelcase(o.getParameters().get(0).toString()));
	                    })
	                )
	        	.addTemplateLocator(
	                ClassPathTemplateLocator.builder()
	                		.setRootPath("templates")
	                		.setSuffix("trimou")
            .build());
        return builder.build();
	}
	
	public static Model buildModel() throws Exception {
		Model model = new Model();
		String specUri = "https://api.swaggerhub.com/apis/rockstar/Collections/1.0.0/swagger.json";
		
		model.setLanguageValue("java");
		model.setLanguageVersion("8");
		
		model.setFrameworkValue("springboot");
		model.setFrameworkVersion("1.5.9");
		
		model.setDatastoreValue("mysql");
		model.setDatastoreVersion("8.5");
		
		model.setHttpValue("http");
		model.setHttpVersion("8.5.27");
		
		model.setDiscoveryValue("eureka");
		model.setDiscoveryVersion("1.5.9");
		
		model.setArchitecture("restapi");
		model.setNamespace("storage");
		model.setOrganization("gravitant");
		
		model.setPackageName("com.rockstar.api");
		
		OpenApi3 openApi = new OpenApi3Parser().parse(new URI(specUri), true);
		if (!openApi.isValid()) {
			for (ValidationItem item : openApi.getValidationItems()) {
				throw new InvalidSchemaException(item.getMsg());
			}
		} else {
			
			OpenApiToArtifactDefinition converter = new OpenApiToArtifactDefinition();
			ArtifactDefinition restapiDefinition = converter.convert(openApi);
			
			System.out.println(ReflectionToStringBuilder.toString(restapiDefinition, ToStringStyle.MULTI_LINE_STYLE));
		}
		
		return model;
	}
	
    public static void main(String args[]) {
      
      try {
    	  	buildModel();
       // System.out.println(templateEngine().getMustache("playground/pojo").render(buildModel()));
      } catch (Exception ex) {
    	  ex.printStackTrace();
      }
	}
  

}


