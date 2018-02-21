package com.rockstar.artifact.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockstar.artifact.RestApiService;
import com.rockstar.artifact.model.SelectedValue;
import com.rockstar.artifact.model.Specification;
import com.rockstar.artifact.web.ArtifactResource;

//@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = RestApiService.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class ArtifactControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private ArtifactResource artifact;
	
	@Before
	public void setUp() {
		Specification spec = new Specification();
		spec.setLocation("https://api.swaggerhub.com/apis/kickster/storage/1.0.0/swagger.json");
		spec.setType("openapi");
		spec.setVersion("3");
		
	    	artifact = new ArtifactResource();
	    	
	    	SelectedValue language = new SelectedValue();
		language.setValue("java");
		language.setVersion("8");
		
		SelectedValue framework = new SelectedValue();
		framework.setValue("springboot");
		framework.setVersion("1.5.9.RELEASE");
		
		SelectedValue datastoreOption = new SelectedValue();
		datastoreOption.setValue("mysql");
		datastoreOption.setVersion("8.5");
		
		SelectedValue httpOption = new SelectedValue();
		httpOption.setValue("tomcat");
		httpOption.setVersion("8.5.20");
		
		SelectedValue discoveryOption = new SelectedValue();
		discoveryOption.setValue("eureka");
		discoveryOption.setVersion("1.5.9");
		
		SelectedValue securityOption = new SelectedValue();
		securityOption.setValue("auth0");
		securityOption.setVersion("Hosted");
		
		SelectedValue ciOption = new SelectedValue();
		ciOption.setValue("travis");
		ciOption.setVersion("Hosted");
		
		SelectedValue cdOption = new SelectedValue();
		cdOption.setValue("dockercloud");
		cdOption.setVersion("Hosted");
		
		SelectedValue registryOption = new SelectedValue();
		registryOption.setValue("docker");
		registryOption.setVersion("Hosted");
		
		SelectedValue scmOption = new SelectedValue();
		scmOption.setValue("github");
		scmOption.setVersion("Hosted");
		
		SelectedValue buildOption = new SelectedValue();
		buildOption.setValue("maven");
		buildOption.setVersion("3.5.2");
		
		SelectedValue testOption = new SelectedValue();
		testOption.setValue("postman");
		testOption.setVersion("0.0.0");
	    
	    	artifact.setNamespace("storage");
	    	artifact.setOrganization("gravitant");
	    	artifact.setArchitecture("restapi");
	    	artifact.setLanguage(language);
	    	artifact.setFramework(framework);
	    	artifact.setSpecification(spec);
	    	artifact.setDatastore(datastoreOption);
	    	artifact.setHttp(httpOption);
		artifact.setDiscovery(discoveryOption);
		artifact.setSecurity(securityOption);
		artifact.setCi(ciOption);
		artifact.setCd(cdOption);
		artifact.setRegistry(registryOption);
		artifact.setScm(scmOption);
		artifact.setBuild(buildOption);
		artifact.setTest(testOption);
    }
	
	@Test
	public void createArtifact() throws Exception {
		String requestBody = this.objectMapper.writeValueAsString(this.artifact);
		System.out.println(requestBody);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/artifacts")
				.accept(MediaType.APPLICATION_JSON).content(requestBody)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/artifacts/1", response.getHeader(HttpHeaders.LOCATION));
	}
   

}
