package com.rockstar.artifact;

import java.net.URI;

import org.springframework.util.StringUtils;
import org.trimou.engine.MustacheEngine;
import org.trimou.engine.MustacheEngineBuilder;
import org.trimou.engine.config.EngineConfigurationKey;
import org.trimou.engine.locator.ClassPathTemplateLocator;
import org.trimou.handlebars.HelpersBuilder;
import org.trimou.handlebars.SimpleHelpers;

import com.reprezen.kaizen.oasparser.OpenApi3Parser;
import com.reprezen.kaizen.oasparser.model3.Contact;
import com.reprezen.kaizen.oasparser.model3.Info;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.reprezen.kaizen.oasparser.val.ValidationResults.ValidationItem;
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
		String specUri = "https://app.swaggerhub.com/apis/kickster/storage/1.0.0/swagger.yaml";
		
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
		
		model.setType("restapi");
		model.setNamespace("storage");
		model.setOrganization("gravitant");
		
		model.setPackageName("com.gravitant.storage");
		
		OpenApi3 openapi3Model = new OpenApi3Parser().parse(new URI(specUri), true);
		if (!openapi3Model.isValid()) {
			for (ValidationItem item : openapi3Model.getValidationItems()) {
				throw new InvalidSchemaException(item.getMsg());
			}
		} else {
			Contact contact = null;
			Info info = null;
			
			info = openapi3Model.getInfo();
			if (info != null) {
				model.setVersion(info.getVersion());
				contact = info.getContact();
				if (contact != null) {
					model.setContact(contact.getEmail());
				}
			}
			model.setClassname("volume");
			model.setSchema(openapi3Model.getSchema("Volume"));
		}
		
		return model;
	}
	
    public static void main(String args[]) {
      
      try {
        System.out.println(templateEngine().getMustache("playground/pojo").render(buildModel()));
      } catch (Exception ex) {
    	  ex.printStackTrace();
      }
	}
    
 

}


