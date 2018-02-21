package com.rockstar.artifact;

import java.net.URI;

import org.trimou.engine.MustacheEngine;

import com.reprezen.kaizen.oasparser.OpenApi3Parser;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.rockstar.artifact.codegen.model.ArtifactDefinition;
import com.rockstar.artifact.converter.openapi.OpenApiToArtifactDefinition;
import com.rockstar.artifact.model.SelectedValue;
import com.rockstar.artifact.model.Specification;
import com.rockstar.artifact.model.TemplateDefinition;
import com.rockstar.artifact.service.TemplateDefinitionRegistry;
import com.rockstar.artifact.web.ArtifactResource;

public class SampleArtifacts {
	
	public static final String DEFAULT_SPEC_URI = "https://api.swaggerhub.com/apis/salsiddiqui0/spirit/1.0.0/swagger.json";
	public static final String STORAGE_SPEC_URI = "https://api.swaggerhub.com/apis/kickster/storage/1.0.0/swagger.json";
	public static final String COLLECTION_SPEC_URI = "https://api.swaggerhub.com/apis/rockstar/Collections/1.0.0/swagger.json";
	public static final String PRODUCT_JSON_SCHEMA = "";
	
	private TemplateDefinitionRegistry templateDefinitionRegistry;
	private MustacheEngine templateEngine;
	private OpenApiToArtifactDefinition openApiConverter;
	
	public SampleArtifacts(TemplateDefinitionRegistry templateDefinitionRegistry, MustacheEngine templateEngine, OpenApiToArtifactDefinition openApiToDefinitionConverter) {
		this.templateDefinitionRegistry = templateDefinitionRegistry;
		this.templateEngine = templateEngine;
		this.openApiConverter = openApiToDefinitionConverter;
	}
	
	public void generateAll() throws Exception {
		this.generateAll(COLLECTION_SPEC_URI);
	}
	
	public void generateAll(String specUri) throws Exception {
		this.generateJavaSpringbootRestApi(specUri);
		//this.generateJavaSpringbootEventListener(specUri);
		//this.generateGolang8GinProject(specUri);
		//this.generateGolang9GorillaProject(specUri);
		//this.generateNodejsExpressProject(specUri);
	}
	
	public void generateJavaSpringbootEventListener(String specUri) throws Exception {
		SelectedValue language = new SelectedValue();
		language.setValue("java");
		language.setVersion("8");
		
		SelectedValue framework = new SelectedValue();
		framework.setValue("springboot");
		framework.setVersion("2.0.0.M7");
		
		String architecture = "eventdriven";
		String namespace = "product";
		String organization = "springframeworkguru";
		
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
		//spec.setVersion("draft-4");
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
		
		this.generateProject(artifact);
	}
	
	public void generateJavaSpringbootRestApi(String specUri) throws Exception {

		SelectedValue language = new SelectedValue();
		language.setValue("java");
		language.setVersion("8");
		
		SelectedValue framework = new SelectedValue();
		framework.setValue("springboot");
		framework.setVersion("1.5.9");
		
		String architecture = "restapi";
		String namespace = "storage";
		String organization = "gravitant";
		
		SelectedValue datastoreOption = new SelectedValue();
		datastoreOption.setValue("mysql");
		datastoreOption.setVersion("8.5");
		
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
		
		ArtifactResource artifact = new ArtifactResource();
		Specification spec = new Specification();
		spec.setLocation(specUri);
		spec.setType("openapi");
		spec.setVersion("3");
		
		artifact.setArchitecture(architecture);
		artifact.setLanguage(language);
		artifact.setFramework(framework);
		artifact.setOrganization(organization);
		artifact.setNamespace(namespace);
		artifact.setSpecification(spec);
		artifact.setDatastore(datastoreOption);
		artifact.setDiscovery(discoveryOption);
		artifact.setSecurity(securityOption);
		artifact.setRegistry(registryOption);
		artifact.setCi(ciOption);
		artifact.setBuild(buildOption);
		artifact.setTest(testOption);
		
		this.generateProject(artifact);
	}
	
	/*public void generateGolang8GinProject(String specUri) {
		String architecture = "coreapi";
		String language = "golang8";
		String namespace = "spirit";
		String framework = "gin";
		String organization = "sfeir";
		Map<String, String> options = new HashMap<String, String> ();
		
		options.put("datastore", "mysql");
		
		ArtifactResource artifact = new ArtifactResource();
		Specification spec = new Specification();
		spec.setLocation(specUri);
		spec.setType("openapi");
		spec.setVersion("3");
		
		artifact.setArchitecture(architecture);
		artifact.setLanguage(language);
		artifact.setFramework(framework);
		artifact.setOrganization(organization);
		artifact.setNamespace(namespace);
		artifact.setSpecification(spec);
		
		this.generateProject(artifact);
		
	}
	
	public void generateGolang9GorillaProject(String specUri) {
		String architecture = "coreapi";
		String language = "golang9";
		String namespace = "spirit";
		String framework = "gorilla";
		String organization = "ibmcloud";
		Map<String, String> options = new HashMap<String, String> ();
		
		options.put("datastore", "mongodb");
		
		ArtifactResource artifact = new ArtifactResource();
		Specification spec = new Specification();
		spec.setLocation(specUri);
		spec.setType("openapi");
		spec.setVersion("3");
		
		artifact.setArchitecture(architecture);
		artifact.setLanguage(language);
		artifact.setFramework(framework);
		artifact.setOrganization(organization);
		artifact.setNamespace(namespace);
		artifact.setSpecification(spec);
		
		this.generateProject(artifact);
		
	}
	
	public void generateNodejsExpressProject(String specUri) {
		String architecture = "coreapi";
		String language = "nodejs";
		String namespace = "spirit";
		String framework = "express";
		String organization = "ibmcloud";
		Map<String, String> options = new HashMap<String, String> ();
		
		options.put("datastore", "mongodb");
		
		ArtifactResource artifact = new ArtifactResource();
		Specification spec = new Specification();
		spec.setLocation(specUri);
		spec.setType("openapi");
		spec.setVersion("3");
		
		artifact.setArchitecture(architecture);
		artifact.setLanguage(language);
		artifact.setFramework(framework);
		artifact.setOrganization(organization);
		artifact.setNamespace(namespace);
		artifact.setSpecification(spec);
		
		this.generateProject(artifact);
		
	}
	*/
	private void generateProject(ArtifactResource artifact) {
		try {
			TemplateDefinition templateDefinition = this.templateDefinitionRegistry.lookup(artifact.getSlug());
			OpenApi3 openApi = new OpenApi3Parser().parse(new URI(artifact.getSpecification().getLocation()), true);
			ArtifactDefinition artifactDefinition = this.openApiConverter.convert(openApi);
			System.out.println(artifactDefinition);
			/**
			GeneratedProject project = new ProjectGenerator(this.templateEngine)
	    			.withType(artifact.getType())
	    			.withLanguage(artifact.getLanguage())
	    			.withNamespace(artifact.getNamespace())
	    			.withOrganization(artifact.getOrganization())
	    			.withDatastore(artifact.getDatastore())
	    			.withHttp(artifact.getHttp())
	    			.withSecurity(artifact.getSecurity())
	    			.withMessaging(artifact.getMessaging())
	    			.withDiscovery(artifact.getDiscovery())
	    			.withCi(artifact.getCi())
	    			.withRegistry(artifact.getRegistry())
	    			.withBuild(artifact.getBuild())
	    			.withTest(artifact.getTest())
	    			.withDefinition(templateDefinition)
	    			.withApiSpec(artifactDefinition)
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
	        */
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
