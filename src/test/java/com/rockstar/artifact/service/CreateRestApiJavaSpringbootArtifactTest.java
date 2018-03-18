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
public class CreateRestApiJavaSpringbootArtifactTest {

	@Inject private ArtifactService artifactService;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void createArtifactTest() throws Exception {
		SelectedValue specification = new SelectedValue();
		specification.setValue("openapi");
		specification.setVersion("3");
		
		SelectedValue architecture = new SelectedValue();
		architecture.setValue("restapi");
		
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
		
		SelectedValue securityOption = new SelectedValue();
		securityOption.setValue("auth0");
		securityOption.setVersion("Hosted");
		
		SelectedValue discoveryOption = new SelectedValue();
		discoveryOption.setValue("eureka");
		discoveryOption.setVersion("1.5.9");
		
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
		artifact.setSchema("https://api.swaggerhub.com/apis/rockstar/Collections/1.0.0/swagger.json");
		artifact.setArchitecture(architecture);
		artifact.setLanguage(language);
		artifact.setFramework(framework);
		artifact.setSpecification(specification);
		artifact.setDatastore(datastoreOption);
		artifact.setDiscovery(discoveryOption);
		artifact.setMessaging(messagingOption);
		artifact.setSecurity(securityOption);
		artifact.setRegistry(registryOption);
		artifact.setCi(ciOption);
		artifact.setBuild(buildOption);
		
		String artifactId = this.artifactService.createArtifact(artifact);
		System.out.println("artifact id " + artifactId);
		
	}
}
