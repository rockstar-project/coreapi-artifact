package com.ibmcloud.kickster.controller;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockstar.artifact.web.ArtifactController;
import com.rockstar.artifact.web.ArtifactResource;

// @RunWith(SpringRunner.class)
// @WebMvcTest(value = ArtifactController.class, secure = false)
public class ArtifactControllerTest {
	
	// @Autowired
	private MockMvc mockMvc;
	
	// @Autowired
	private ObjectMapper objectMapper;
	
	private ArtifactResource artifact;
	
	// @Before
	public void setUp() {
    	artifact = new ArtifactResource();
    	Map<String, String> options = new HashMap<String, String>();
    	options.put("datastore", "mysql");
    	options.put("webserver", "tomcat");
    	options.put("messaging", "rabbitmq");
    	options.put("registry", "eureka");
    	options.put("scm", "github");
    	options.put("ci", "travisci");
    	options.put("artifactory", "jfrog");
    	options.put("build", "maven");
    	options.put("test", "postman");
    	options.put("package", "docker");
    	artifact.setNamespace("riaas");
    	artifact.setOrganization("ibmcloud");
    	artifact.setType("restapi");
    	artifact.setLanguage("java8");
    	artifact.setFramework("springboot");
    	artifact.setSpecification("http://petstore.swagger.io/v2/swagger.json");
    	artifact.setOptions(options);
    }
	
	// @Test
	public void createArtifact() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/artifacts")
				.accept(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(this.artifact))
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/artifacts/1", response.getHeader(HttpHeaders.LOCATION));
	}
   

}
