package com.ibmcloud.kickster.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.trimou.engine.MustacheEngine;
import org.trimou.engine.MustacheEngineBuilder;
import org.trimou.engine.config.EngineConfigurationKey;
import org.trimou.engine.locator.ClassPathTemplateLocator;
import org.trimou.handlebars.HelpersBuilder;
import org.trimou.handlebars.SimpleHelpers;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.ibmcloud.kickster.SampleArtifacts;
import com.ibmcloud.kickster.model.TemplateDefinition;
import com.ibmcloud.kickster.model.TemplateDefinitionRegistry;
import com.ibmcloud.kickster.util.CheckUtils;
import com.ibmcloud.kickster.util.WordUtils;

@Configuration
public class ServiceConfig {
	
	@Bean
	public TemplateDefinitionRegistry templateDefinitionRegistry() throws Exception {
		TemplateDefinitionRegistry templateDefinitionRegistry = null;
		CollectionType definitionCollectionType = null;
		List<TemplateDefinition> templateDefinitionList = null;
		ObjectMapper jsonMapper = new ObjectMapper();
	    jsonMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
	    jsonMapper.setSerializationInclusion(Include.NON_NULL);
		
		definitionCollectionType = jsonMapper.getTypeFactory().constructCollectionType(List.class, TemplateDefinition.class);
		templateDefinitionList = jsonMapper.readValue(this.workspaceResource().getInputStream(), definitionCollectionType);
		templateDefinitionRegistry = new TemplateDefinitionRegistry();
		templateDefinitionRegistry.registerTemplateDefinitions(templateDefinitionList);
	
		return templateDefinitionRegistry;
	}
	
	@Bean
	public ClassPathResource workspaceResource() throws Exception {
		return new ClassPathResource("definitions.json");
	}
	
	@Bean
	public MustacheEngine templateEngine() throws Exception {
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
	
	@Bean(initMethod="generateAll")
	public SampleArtifacts sampleArtifacts() throws Exception {
		return new SampleArtifacts(this.templateDefinitionRegistry(), this.templateEngine());
	}

}