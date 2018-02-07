package com.rockstar.artifact.service;

import java.io.File;
import java.io.FileOutputStream;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.trimou.engine.MustacheEngine;

import com.rockstar.artifact.model.GeneratedFile;
import com.rockstar.artifact.model.GeneratedProject;
import com.rockstar.artifact.model.NotFoundException;
import com.rockstar.artifact.model.ProjectGenerator;
import com.rockstar.artifact.model.Specification;
import com.rockstar.artifact.model.TemplateDefinition;
import com.rockstar.artifact.model.TemplateDefinitionRegistry;
import com.rockstar.artifact.util.ZipUtils;
import com.rockstar.artifact.web.ArtifactResource;


@Service
public class ArtifactServiceImpl implements ArtifactService {
	
	static Logger LOGGER = LoggerFactory.getLogger(ArtifactService.class);
	
	@Inject 
	private MustacheEngine templateEngine;
	
	@Inject
	private TemplateDefinitionRegistry templateDefinitionRegistry;
	 
    public String createArtifact(ArtifactResource artifact) throws Exception {
	    	GeneratedProject project = null;
	    	TemplateDefinition templateDefinition = null;
	    	Specification specification = null;
	    	String specificationLocation = null;
	    	
	    	if (artifact != null) {
		    	specification = artifact.getSpecification();
		    	if (specification != null) {
		    		specificationLocation = specification.getLocation();
		    
			    	templateDefinition = this.templateDefinitionRegistry.lookup(artifact.getSlug());
			    	project = new ProjectGenerator(this.templateEngine)
			    			.withType(artifact.getType())
			    			.withLanguage(artifact.getLanguage())
			    			.withNamespace(artifact.getNamespace())
			    			.withOrganization(artifact.getOrganization())
			    			.withDatastore(artifact.getDatastore())
			    			.withHttp(artifact.getHttp())
			    			.withMessaging(artifact.getMessaging())
			    			.withDiscovery(artifact.getDiscovery())
			    			.withMonitoring(artifact.getMonitoring())
			    			.withSecurity(artifact.getSecurity())
			    			.withTracing(artifact.getTracing())
			    			.withCi(artifact.getCi())
			    			.withCd(artifact.getCd())
			    			.withRegistry(artifact.getRegistry())
			    			.withScm(artifact.getScm())
			    			.withBuild(artifact.getBuild())
			    			.withTest(artifact.getTest())
			    			.withDefinition(templateDefinition)
			    			.withSpec(specificationLocation)
			    		.generate();
		    	
			    	String directory = this.createProjectFiles(project);
			        
			    	ZipUtils.zip(directory, directory + ".zip");
			    	FileUtils.deleteDirectory(new File(directory));
		    	}
	    	}
        return project.getId();
    }
    
    
    public byte[] getArtifact(String artifactId) throws Exception {
    	byte[] fileContent = null;
    	
    	Resource fileResource = null;
    	File outputFile = null;
    	
    	outputFile = new File(FileUtils.getTempDirectory().getAbsolutePath() + File.separator + artifactId + ".zip");
    	if (outputFile.exists()) {
    		fileResource = new FileSystemResource(outputFile);
			fileContent = IOUtils.toByteArray(fileResource.getInputStream());
    	} else {
    		throw new NotFoundException("artifact", artifactId);
    	}
    	 
    	return fileContent;
    }
    
    private String createProjectFiles(GeneratedProject project) throws Exception {
		String outputPath = FileUtils.getTempDirectory().getAbsolutePath() + File.separator + project.getId();
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
		return outputPath;
    }
   
}

