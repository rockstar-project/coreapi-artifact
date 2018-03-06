package com.rockstar.artifact.controller;

import java.net.URI;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.reprezen.kaizen.oasparser.OpenApi3Parser;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.rockstar.artifact.codegen.model.SpecDefinitions;
import com.rockstar.artifact.converter.openapi.OpenApiToSpecDefinitions;
import com.rockstar.artifact.model.GeneratedProject;
import com.rockstar.artifact.model.Project;
import com.rockstar.artifact.model.ProjectBuilder;
import com.rockstar.artifact.model.SelectedValue;
import com.rockstar.artifact.model.Specification;
import com.rockstar.artifact.service.CodeGenerator;
import com.rockstar.artifact.utils.DownloadUtils;
import com.rockstar.artifact.web.ArtifactResource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateJavaSpringbootRestApiTest {
	
	public static final String DEFAULT_SPEC_URI = "https://api.swaggerhub.com/apis/salsiddiqui0/spirit/1.0.0/swagger.json";
	public static final String STORAGE_SPEC_URI = "https://api.swaggerhub.com/apis/rockstar/Storage/1.0.0/swagger.json";
	public static final String COLLECTION_SPEC_URI = "https://api.swaggerhub.com/apis/rockstar/Collections/1.0.0/swagger.json";
	public static final String PRODUCT_SPEC_URI = "https://api.swaggerhub.com/apis/rockstar/Products/1.0.0/swagger.json";
	public static final String PRODUCT_JSON_SCHEMA = "";
	
	@Inject private OpenApiToSpecDefinitions openApiConverter;
	@Inject private CodeGenerator codeGenerator;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void generateJavaSpringbootRestApi() throws Exception {

		SelectedValue language = new SelectedValue();
		language.setValue("java");
		language.setVersion("8");
		
		SelectedValue framework = new SelectedValue();
		framework.setValue("springboot");
		framework.setVersion("1.5.9.RELEASE");
		
		String architecture = "restapi";
		String namespace = "collection";
		String organization = "cookery";
		
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
		
		SelectedValue discoveryOption = new SelectedValue();
		discoveryOption.setValue("eureka");
		discoveryOption.setVersion("1.5.9");
		
		SelectedValue messagingOption = new SelectedValue();
		messagingOption.setValue("rabbitmq");
		messagingOption.setVersion("3.7.2");
		
		ArtifactResource artifact = new ArtifactResource();
		Specification spec = new Specification();
		spec.setLocation(COLLECTION_SPEC_URI);
		spec.setType("openapi");
		spec.setVersion("3");
		
		artifact.setArchitecture(architecture);
		artifact.setLanguage(language);
		artifact.setFramework(framework);
		artifact.setOrganization(organization);
		artifact.setNamespace(namespace);
		artifact.setSpecification(spec);
		artifact.setDatastore(datastoreOption);
		artifact.setMessaging(messagingOption);
		artifact.setDiscovery(discoveryOption);
		artifact.setSecurity(securityOption);
		artifact.setRegistry(registryOption);
		artifact.setCi(ciOption);
		artifact.setBuild(buildOption);
		artifact.setTest(testOption);
		//this.artifactService.createArtifact(artifact);
		this.generateProject(artifact);
	}
	
	public void generateProject(ArtifactResource artifact) throws Exception {
	    	Project project = null;
		SpecDefinitions specDefinition = null;
		
		try {
		    	if (artifact != null) {
			   
		    		OpenApi3 openApi = new OpenApi3Parser().parse(new URI(artifact.getSpecification().getLocation()), true);
		    		specDefinition = this.openApiConverter.convert(openApi);
			    	project = new ProjectBuilder()
			    			.withArchitecture(artifact.getArchitecture())
			    			.withLanguage(artifact.getLanguage())
			    			.withFramework(artifact.getFramework())
			    			.withNamespace(artifact.getNamespace())
			    			.withOrganization(artifact.getOrganization())
			    			.withDatastore(artifact.getDatastore())
			    			.withHttp(artifact.getHttp())
			    			.withMessaging(artifact.getMessaging())
			    			.withDiscovery(artifact.getDiscovery())
			    			//.withMonitoring(artifact.getMonitoring())
			    			.withSecurity(artifact.getSecurity())
			    			//.withTracing(artifact.getTracing())
			    			.withCi(artifact.getCi())
			    			//.withCd(artifact.getCd())
			    			.withRegistry(artifact.getRegistry())
			    			//.withScm(artifact.getScm())
			    			.withBuild(artifact.getBuild())
			    			//.withTest(artifact.getTest())
			    			.withApiSpec(specDefinition)
			    		.build();
			    		GeneratedProject generatedProject = this.codeGenerator.generate(project);
		    	
				    	DownloadUtils.createZipfile(generatedProject, artifact.getArchitecture() + "-" + artifact.getNamespace());
		    	}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
