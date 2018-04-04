package com.rockstar.artifact;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.util.Collection;

import javax.imageio.ImageIO;
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
import com.rockstar.artifact.codegen.model.MicroserviceDefinition;
import com.rockstar.artifact.converter.openapi.OpenApiToRestApiMicroserviceDefinition;
import com.rockstar.artifact.model.Model;
import com.rockstar.artifact.model.SelectedValue;
import com.rockstar.artifact.service.InvalidSchemaException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TrimouTemplateTest {
	
	@Inject private OpenApiToRestApiMicroserviceDefinition openApiToSpecDefinitions;
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
		
		model.setHttpValue("undertow");
		model.setHttpVersion("8.5.27");
		
		model.setDiscoveryValue("eureka");
		model.setDiscoveryVersion("1.5.9");
		
		//model.setMonitoringValue("prometheus");
		//model.setMonitoringVersion("0.1.0");
		
		model.setArchitecture("restapi");
		model.setNamespace("collection");
		model.setOrganization("cookery");
		
		model.setPackageName("com.cookery.recipe");
		
		OpenApi3 openApi = new OpenApi3Parser().parse(new URI(specUri), true);
		if (!openApi.isValid()) {
			for (ValidationItem item : openApi.getValidationItems()) {
				throw new InvalidSchemaException(item.getMsg());
			}
		} else {
			// STATIC TEMPLATES
			//this.renderTemplate("java-springboot/maven", model);
			//this.renderTemplate("java-springboot/enum_validator", model);
			this.renderTemplate("java-springboot/restapi", model);
			
			// ENTITY COMPONENT TEMPLATES
			//this.renderTemplate(Definition.Type.Entity, "java-springboot/mysql_entity", openApi, model);
			// NOT TESTED: this.renderTemplate(Definition.Type.Repository, "java-springboot/mysql_schema", openApi, model);
		    		
			// REPOSITORY COMPONENT TEMPLATES
			//this.renderTemplate(Definition.Type.Repository, "java-springboot/mysql_repository", openApi, model);
			
			// SERVICE COMPONENT TEMPLATES
			//this.renderTemplate(Definition.Type.Service, "java-springboot/search_criteria", openApi, model);
			//this.renderTemplate(Definition.Type.Service, "java-springboot/service_interface", openApi, model);
			//this.renderTemplate(Definition.Type.Service, "java-springboot/service_impl", openApi, model);
			//this.renderTemplate(Definition.Type.Service, "java-springboot/search_specification", openApi, model);
			
			// RESOURCE COMPONENT TEMPLATES
			//this.renderTemplate(Definition.Type.Resource, "java-springboot/resource", openApi, model);
			// this.renderTemplate(Definition.Type.Resource, "java-springboot/resource_assembler", openApi, model);
			
			// CONTROLLER COMPONENT TEMPLATE
			//this.renderTemplate(Definition.Type.Controller, "java-springboot/controller", openApi, model);
			// MYSQL Table COMPONENT TEMPLATE
			//this.renderTemplate(Definition.Type.MySqlSchema, "java-springboot/mysql_schema", openApi, model);
			
			//this.renderTemplate(Definition.Type.Resource, "java-springboot/messages", openApi, model);
		}
	}
	
	private void renderTemplate(String template, Model model) {
		String generatedCode = templateEngine.getMustache(template).render(model);
		System.out.println(generatedCode);
	}
	
	private void renderTemplate(Definition.Type type, String template, OpenApi3 openApi, Model model) {
		MicroserviceDefinition specDefinitions = this.openApiToSpecDefinitions.convert(openApi);
		Collection<Definition> definitions = specDefinitions.getDefinitions(type);
		if (definitions != null && !definitions.isEmpty()) {
			for (Definition current : definitions) {
	    			model.setName(StringUtils.lowerCase(current.getName()));
	    			model.setDefinition(current);
	    			String generatedCode = templateEngine.getMustache(template).render(model);
	    			System.out.println(generatedCode);
	    		}
		}
	}
	
	private void renderImageTemplate(Definition.Type type, String template, OpenApi3 openApi, Model model) {
		MicroserviceDefinition specDefinitions = this.openApiToSpecDefinitions.convert(openApi);
		Collection<Definition> definitions = specDefinitions.getDefinitions(type);
		if (definitions != null && !definitions.isEmpty()) {
			for (Definition current : definitions) {
	    			model.setName(StringUtils.lowerCase(current.getName()));
	    			model.setDefinition(current);
	    			String generatedCode = templateEngine.getMustache(template).render(model);
	    			System.out.println(generatedCode);
	    			
	    			try {
	    				String fullPath = System.getProperty("user.home") + File.separator + "generated-image/demo.png";
		    			//OutputStream outputStream = Files.newOutputStream(Paths.get(fullPath));
	    				//outputStream.write(generatedCode.getBytes());
		    			BufferedImage bufferedImage = new BufferedImage(960, 640, BufferedImage.TYPE_INT_ARGB);
		    			Graphics graphics = bufferedImage.getGraphics();
		    			graphics.drawString(generatedCode, 10, 20);
		    			
		    			ImageIO.write(bufferedImage,  "png", new File(fullPath));
	    			} catch (Exception ex) {
	    				ex.printStackTrace();
	    			}
	    			
	    		}
		}
	}
	
}


