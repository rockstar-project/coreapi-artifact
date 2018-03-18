package com.rockstar.artifact.service;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rockstar.artifact.model.SelectedValue;
import com.rockstar.artifact.web.ArtifactResource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateEventDrivenJavaSpringbootArtifactTest {
	
	public static final String PRODUCT_JSON_SCHEMA = "";
	public static final String CALENDAR_JSON_SCHEMA = "http://json-schema.org/example/calendar.json";
	public static final String CARD_JSON_SCHEMA = "http://json-schema.org/example/card.json";
	public static final String GEO_LOCATION_JSON_SCHEMA = "http://json-schema.org/example/geo.json";
	public static final String ADDRESS_JSON_SCHEMA = "http://json-schema.org/example/address.json";
	
	@Inject private ArtifactService artifactService;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void createArtifactTest() throws Exception {
		SelectedValue specification = new SelectedValue();
		specification.setValue("jsonschema");
		specification.setVersion("draft-06");
		
		SelectedValue architecture = new SelectedValue();
		architecture.setValue("eventdriven");
		
		SelectedValue language = new SelectedValue();
		language.setValue("java");
		language.setVersion("8");
		
		SelectedValue framework = new SelectedValue();
		framework.setValue("springboot");
		framework.setVersion("1.5.9.RELEASE");
		
		SelectedValue datastoreOption = new SelectedValue();
		datastoreOption.setValue("mysql");
		datastoreOption.setVersion("5.7");
		
		//datastoreOption.setValue("cassandra");
		//datastoreOption.setVersion("3.11");
		
		//datastoreOption.setValue("mongodb");
		//datastoreOption.setVersion("3.6");
		
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
		
		SelectedValue messagingOption = new SelectedValue();
		messagingOption.setValue("rabbitmq");
		messagingOption.setVersion("3.7.2");
		
		ArtifactResource artifact = new ArtifactResource();
		
		artifact.setName("collection");
		artifact.setOrganization("cookery");
		artifact.setSchema(CARD_JSON_SCHEMA);
		artifact.setArchitecture(architecture);
		artifact.setLanguage(language);
		artifact.setFramework(framework);
		artifact.setSpecification(specification);
		artifact.setDatastore(datastoreOption);
		artifact.setMessaging(messagingOption);
		artifact.setRegistry(registryOption);
		artifact.setCi(ciOption);
		artifact.setBuild(buildOption);
		
		String artifactId = this.artifactService.createArtifact(artifact);
		System.out.println("artifact id " + artifactId);
		
	}

}
