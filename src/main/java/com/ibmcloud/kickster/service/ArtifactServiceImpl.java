package com.ibmcloud.kickster.service;

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

import com.ibmcloud.kickster.model.GeneratedFile;
import com.ibmcloud.kickster.model.GeneratedProject;
import com.ibmcloud.kickster.model.NotFoundException;
import com.ibmcloud.kickster.model.ProjectGenerator;
import com.ibmcloud.kickster.model.TemplateDefinitionRegistry;
import com.ibmcloud.kickster.model.TemplateDefinition;
import com.ibmcloud.kickster.util.ZipUtils;
import com.ibmcloud.kickster.web.ArtifactResource;


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
    	
    	templateDefinition = this.templateDefinitionRegistry.lookup(artifact.getSlug());
    	project = new ProjectGenerator(this.templateEngine)
    			.withType(artifact.getType())
    			.withLanguage(artifact.getLanguage())
    			.withNamespace(artifact.getNamespace())
    			.withOrganization(artifact.getOrganization())
    			.withOptions(artifact.getOptions())
    			.withDefinition(templateDefinition)
    			.withSpec(artifact.getSpecification())
    		.generate();
    	
    	String directory = this.createProjectFiles(project);
        
    	ZipUtils.zip(directory, directory + ".zip");
    	FileUtils.deleteDirectory(new File(directory));
        
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

