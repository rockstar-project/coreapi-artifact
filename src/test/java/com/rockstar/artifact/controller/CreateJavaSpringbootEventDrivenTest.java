package com.rockstar.artifact.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.rockstar.artifact.model.SelectedValue;
import com.rockstar.artifact.model.Specification;
import com.rockstar.artifact.service.ArtifactService;
import com.rockstar.artifact.web.ArtifactResource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateJavaSpringbootEventDrivenTest {
	
	@Inject private ArtifactService artifactService;
	@Test
	public void testGeneratePojoClasses() {
		/**
	 	File outputFile = new File(FileUtils.getTempDirectory().getAbsolutePath());
	    try {
			ClassPathResource jsonSchemaResource = new ClassPathResource("/jsonschema/product.json");
			JSONObject inputSchema = new JSONObject(new JSONTokener(jsonSchemaResource.getInputStream()));
			SchemaLoader loader = SchemaLoader.builder()
	                .schemaJson(inputSchema)
	                .build();
			Schema schema = loader.load().build();
			
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		*/
		
		
		
		ClassPathResource jsonSchemaResource = new ClassPathResource("/jsonschema/product.json");
		try {
			JsonReader reader = new JsonReader(new InputStreamReader(jsonSchemaResource.getInputStream(), "UTF-8"));
			JsonParser parser = new JsonParser();
			JsonObject schema=parser.parse( reader ).getAsJsonObject();
			Gson gson=new GsonBuilder().setPrettyPrinting().create();
			Type listType = new TypeToken<Map<String, JsonSchemaDefinition>>() {}.getType();
			Map<String, JsonSchemaDefinition> propertyDefinitions = gson.fromJson(schema.getAsJsonObject("definitions"), listType);
			if (propertyDefinitions != null && !propertyDefinitions.isEmpty()) {
				System.out.println("total count: " + propertyDefinitions.size());
			}
			for (Entry<String, JsonSchemaDefinition> entry : propertyDefinitions.entrySet()) {
				System.out.println(entry.getValue());
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void generateJavaSpringbootEventListener(String specUri) throws Exception {
		SelectedValue language = new SelectedValue();
		language.setValue("java");
		language.setVersion("8");
		
		SelectedValue framework = new SelectedValue();
		framework.setValue("springboot");
		framework.setVersion("1.5.9");
		
		String architecture = "eventdriven";
		String namespace = "product";
		String organization = "codefly";
		
		SelectedValue datastoreOption = new SelectedValue();
		datastoreOption.setValue("mysql");
		datastoreOption.setVersion("8.5");
		
		SelectedValue buildOption = new SelectedValue();
		buildOption.setValue("maven");
		buildOption.setVersion("3.5.2");
		
		SelectedValue testOption = new SelectedValue();
		testOption.setValue("postman");
		testOption.setVersion("0.0.0");
		
		SelectedValue ciOption = new SelectedValue();
		ciOption.setValue("travis");
		ciOption.setVersion("Hosted");
		
		SelectedValue registryOption = new SelectedValue();
		registryOption.setValue("docker");
		registryOption.setVersion("Hosted");
		ArtifactResource artifact = new ArtifactResource();
		Specification spec = new Specification();
		spec.setLocation(specUri);
		//spec.setType("jsonschema");
		//spec.setVersion("draft-04");
		spec.setType("openapi");
		spec.setVersion("3");
		
		artifact.setArchitecture(architecture);
		artifact.setLanguage(language);
		artifact.setFramework(framework);
		artifact.setOrganization(organization);
		artifact.setNamespace(namespace);
		artifact.setSpecification(spec);
		artifact.setDatastore(datastoreOption);
		artifact.setRegistry(registryOption);
		artifact.setCi(ciOption);
		artifact.setBuild(buildOption);
		artifact.setTest(testOption);
		
		this.artifactService.createArtifact(artifact);
	}

}
