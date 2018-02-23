package com.rockstar.artifact.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;

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

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateJavaSpringbootEventDrivenTest {
	
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

}
