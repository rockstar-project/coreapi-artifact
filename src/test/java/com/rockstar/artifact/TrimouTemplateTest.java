package com.rockstar.artifact;

import java.net.URI;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.trimou.engine.MustacheEngine;

import com.reprezen.kaizen.oasparser.OpenApi3Parser;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.reprezen.kaizen.oasparser.val.ValidationResults.ValidationItem;
import com.rockstar.artifact.codegen.model.Definition;
import com.rockstar.artifact.codegen.model.SpecDefinitions;
import com.rockstar.artifact.converter.openapi.OpenApiToSpecDefinitions;
import com.rockstar.artifact.model.InvalidSchemaException;
import com.rockstar.artifact.model.Model;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrimouTemplateTest {
	
	@Inject private OpenApiToSpecDefinitions openApiToSpecDefinitions;
	@Inject private MustacheEngine templateEngine;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testSingleTemplate() throws Exception {
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
		model.setOrganization("ibmcloud");
		
		model.setPackageName("com.ibmcloud.storage");
		
		OpenApi3 openApi = new OpenApi3Parser().parse(new URI(specUri), true);
		if (!openApi.isValid()) {
			for (ValidationItem item : openApi.getValidationItems()) {
				throw new InvalidSchemaException(item.getMsg());
			}
		} else {
			// ENTITY COMPONENT TEMPLATES
			//this.renderTemplate(Definition.Type.Repository, "mysql_entity", openApi, model);
			// NOT TESTED: this.renderTemplate(Definition.Type.Repository, "mysql_schema", openApi, model);
		    		
			// REPOSITORY COMPONENT TEMPLATES
			// this.renderTemplate(Definition.Type.Repository, "mysql_repository", openApi, model);
			
			// SERVICE COMPONENT TEMPLATES
			//this.renderTemplate(Definition.Type.Service, "search_criteria", openApi, model);
			//this.renderTemplate(Definition.Type.Service, "service_interface", openApi, model);
			this.renderTemplate(Definition.Type.Service, "service_impl", openApi, model);
			//this.renderTemplate(Definition.Type.Service, "search_specification", openApi, model);
			
			// RESOURCE COMPONENT TEMPLATES
			//this.renderTemplate(Definition.Type.Resource, "resource", openApi, model);
			// this.renderTemplate(Definition.Type.Resource, "resource_assembler", openApi, model);
			
			// CONTROLLER COMPONENT TEMPLATE
			//this.renderTemplate(Definition.Type.Controller, "controller", openApi, model);
		}
	}
	
	private void renderTemplate(Definition.Type type, String template, OpenApi3 openApi, Model model) {
		SpecDefinitions specDefinitions = this.openApiToSpecDefinitions.convert(openApi);
		Collection<Definition> definitions = specDefinitions.getDefinitions(type);
		if (definitions != null && !definitions.isEmpty()) {
			for (Definition current : definitions) {
	    			model.setName(StringUtils.lowerCase(current.getName()));
	    			model.setDefinition(current);
	    		
	    			System.out.println(templateEngine.getMustache("java-springboot/" + template).render(model));
	    		}
		}
	}

}


