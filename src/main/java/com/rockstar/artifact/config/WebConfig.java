package com.rockstar.artifact.config;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

@Configuration
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Inject private ObjectMapper objectMapper;
	
	@PostConstruct
	public void initialize() {
	    this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
	    this.objectMapper.setSerializationInclusion(Include.NON_NULL);
	    this.objectMapper.registerModule(new JodaModule());
	    this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}	
}