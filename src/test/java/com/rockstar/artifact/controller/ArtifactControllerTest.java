package com.rockstar.artifact.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
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
import com.rockstar.artifact.web.ArtifactResource;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = RestApiService.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class ArtifactControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void createArtifact() throws Exception {
		ClassPathResource createArtifactRequest = new ClassPathResource("/data/restapi-java-springboot.json");
		ObjectMapper objectMapper = new ObjectMapper();
		ArtifactResource artifactResource = objectMapper.readValue(createArtifactRequest.getInputStream(), ArtifactResource.class);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/artifacts")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(artifactResource))
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/artifacts/1", response.getHeader(HttpHeaders.LOCATION));
	}
   
}
