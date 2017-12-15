package com.ibmcloud.kickster;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.trimou.engine.MustacheEngine;

import com.ibmcloud.kickster.model.GeneratedFile;
import com.ibmcloud.kickster.model.GeneratedProject;
import com.ibmcloud.kickster.model.ProjectGenerator;
import com.ibmcloud.kickster.model.TemplateDefinition;
import com.ibmcloud.kickster.model.TemplateDefinitionRegistry;
import com.ibmcloud.kickster.util.ZipUtils;
import com.ibmcloud.kickster.web.ArtifactResource;

public class SampleArtifacts {
	
	public static final String DEFAULT_SPEC_URI = "https://api.swaggerhub.com/apis/salsiddiqui0/spirit/1.0.0/swagger.json";
	public static final String STORAGE_SPEC_URI = "https://api.swaggerhub.com/apis/kickster/storage/1.0.0/swagger.json";
	
	private TemplateDefinitionRegistry templateDefinitionRegistry;
	private MustacheEngine templateEngine;
	
	public SampleArtifacts(TemplateDefinitionRegistry templateDefinitionRegistry, MustacheEngine templateEngine) {
		this.templateDefinitionRegistry = templateDefinitionRegistry;
		this.templateEngine = templateEngine;
	}
	
	public void generateAll() throws Exception {
		this.generateAll(STORAGE_SPEC_URI);
	}
	
	public void generateAll(String specUri) throws Exception {
		this.generateJava8SpringbootProject(specUri);
		this.generateGolang8GinProject(specUri);
		this.generateGolang9GorillaProject(specUri);
		this.generateNodejsExpressProject(specUri);
	}
	
	public void generateJava8SpringbootProject(String specUri) throws Exception {
		Map<String, String> options = new HashMap<String, String> ();
		
		String type = "coreapi";
		String language = "java8";
		String namespace = "spirit";
		String framework = "springboot";
		String organization = "ibmcloud";
		
		options.put("datastore", "mysql");
		options.put("web", "hateoas");
		options.put("discovery", "eureka");
		
		ArtifactResource artifact = new ArtifactResource();
		
		artifact.setType(type);
		artifact.setLanguage(language);
		artifact.setFramework(framework);
		artifact.setOrganization(organization);
		artifact.setNamespace(namespace);
		artifact.setSpecification(specUri);
		
		this.generateProject(artifact);
	}
	
	public void generateGolang8GinProject(String specUri) {
		String type = "coreapi";
		String language = "golang8";
		String namespace = "spirit";
		String framework = "gin";
		String organization = "sfeir";
		Map<String, String> options = new HashMap<String, String> ();
		
		options.put("datastore", "mysql");
		
		ArtifactResource artifact = new ArtifactResource();
		
		artifact.setType(type);
		artifact.setLanguage(language);
		artifact.setFramework(framework);
		artifact.setOrganization(organization);
		artifact.setNamespace(namespace);
		artifact.setSpecification(specUri);
		
		this.generateProject(artifact);
		
	}
	
	public void generateGolang9GorillaProject(String specUri) {
		String type = "coreapi";
		String language = "golang9";
		String namespace = "spirit";
		String framework = "gorilla";
		String organization = "ibmcloud";
		Map<String, String> options = new HashMap<String, String> ();
		
		options.put("datastore", "mongodb");
		
		ArtifactResource artifact = new ArtifactResource();
		
		artifact.setType(type);
		artifact.setLanguage(language);
		artifact.setFramework(framework);
		artifact.setOrganization(organization);
		artifact.setNamespace(namespace);
		artifact.setSpecification(specUri);
		
		this.generateProject(artifact);
		
	}
	
	public void generateNodejsExpressProject(String specUri) {
		String type = "coreapi";
		String language = "nodejs";
		String namespace = "spirit";
		String framework = "express";
		String organization = "ibmcloud";
		Map<String, String> options = new HashMap<String, String> ();
		
		options.put("datastore", "mongodb");
		
		ArtifactResource artifact = new ArtifactResource();
		
		artifact.setType(type);
		artifact.setLanguage(language);
		artifact.setFramework(framework);
		artifact.setOrganization(organization);
		artifact.setNamespace(namespace);
		artifact.setSpecification(specUri);
		
		this.generateProject(artifact);
		
	}
	
	private void generateProject(ArtifactResource artifact) {
		try {
			TemplateDefinition templateDefinition = this.templateDefinitionRegistry.lookup(artifact.getSlug());
			GeneratedProject project = new ProjectGenerator(this.templateEngine)
	    			.withType(artifact.getType())
	    			.withLanguage(artifact.getLanguage())
	    			.withNamespace(artifact.getNamespace())
	    			.withOrganization(artifact.getOrganization())
	    			.withOptions(artifact.getOptions())
	    			.withDefinition(templateDefinition)
	    			.withSpec(artifact.getSpecification())
				.generate();
			
			
			String outputPath = FileUtils.getUserDirectoryPath() + File.separator + "Downloads" + File.separator + artifact.getSlug();
			FileOutputStream outputFileStream = null;
			File outputDirectory = null;
			File outputFile = null;
			
			for (GeneratedFile current : project.getFiles()) {
				outputDirectory = new File(outputPath + File.separator + current.getPath());
				outputDirectory.mkdirs();
				outputFile = new File(outputDirectory, current.getFilename());
				if (!outputFile.exists()) {
					outputFile.createNewFile();
				}
				outputFileStream = new FileOutputStream(outputFile);
				outputFileStream.write(current.getContent().getBytes());
				outputFileStream.close();
			}
			
			ZipUtils.zip(outputPath, outputPath + ".zip");
	    	FileUtils.deleteDirectory(new File(outputPath));
	        
	        System.out.println(outputPath + ".zip");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
