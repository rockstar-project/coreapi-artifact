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

import com.rockstar.artifact.codegen.model.MicroserviceDefinition;
import com.rockstar.artifact.converter.SpecificationToMicroserviceDefinition;
import com.rockstar.artifact.model.GeneratedFile;
import com.rockstar.artifact.model.GeneratedProject;
import com.rockstar.artifact.model.NotFoundException;
import com.rockstar.artifact.model.Project;
import com.rockstar.artifact.model.ProjectBuilder;
import com.rockstar.artifact.model.SelectedValue;
import com.rockstar.artifact.model.Specification;
import com.rockstar.artifact.util.ZipUtils;
import com.rockstar.artifact.web.ArtifactResource;

@Service
public class ArtifactServiceImpl implements ArtifactService {

	static Logger LOGGER = LoggerFactory.getLogger(ArtifactService.class);

	@Inject private CodeGenerator codeGenerator;
	@Inject private SpecificationToMicroserviceDefinition schemaToDefinition;

	public String createArtifact(ArtifactResource artifact) throws Exception {
		String artifactId = null;
		Project project = null;
		GeneratedProject generatedProject = null;
		String directory = null;
		
		if (artifact != null) {
			project = new ProjectBuilder()
					.withArchitecture(artifact.getArchitecture())
					.withLanguage(artifact.getLanguage())
					.withFramework(artifact.getFramework())
					.withNamespace(artifact.getName())
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
					.withDefinition(this.createMicroserviceDefinition(artifact.getSchema(), artifact.getSpecification()))
					.build();

			generatedProject = this.codeGenerator.generate(project);
			directory = this.createProjectFiles(generatedProject);
			System.out.println("output dir: " + directory + ".zip");
			ZipUtils.zip(directory, directory + ".zip");
			FileUtils.deleteDirectory(new File(directory));
			artifactId = generatedProject.getId();
		}

		return artifactId;
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
	
	private MicroserviceDefinition createMicroserviceDefinition(String schema, SelectedValue value) throws Exception {
		Specification specification = null;
		
		specification = new Specification();
		specification.setType(value.getValue());
		specification.setLocation(schema);
		specification.setVersion(value.getVersion());
		
		return this.schemaToDefinition.convert(specification);
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