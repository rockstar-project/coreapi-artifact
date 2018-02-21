package com.rockstar.artifact.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.trimou.Mustache;
import org.trimou.engine.MustacheEngine;

import com.rockstar.artifact.codegen.model.ArtifactDefinition;
import com.rockstar.artifact.model.Directory;
import com.rockstar.artifact.model.GeneratedFile;
import com.rockstar.artifact.model.GeneratedProject;
import com.rockstar.artifact.model.Model;
import com.rockstar.artifact.model.NotFoundException;
import com.rockstar.artifact.model.SelectedValue;
import com.rockstar.artifact.model.TemplateDefinition;
import com.rockstar.artifact.model.TemplateFile;
import com.rockstar.artifact.util.CheckUtils;
import com.rockstar.artifact.util.WordUtils;

public class ProjectGenerator {
	
	private static final String PACKAGE_PREFIX = "com";
	private static final String PACKAGE_PLACEHOLDER = "com.{organization}.{namespace}";
	private static final String NAME_PLACEHOLDER = "{name}";
	
	private Model model;
	private TemplateDefinition projectTemplate;
	private MustacheEngine engine;
	
	public ProjectGenerator(MustacheEngine engine) {
		this.engine = engine;
		this.model = new Model();
	}
	
	public ProjectGenerator withDefinition(TemplateDefinition template) {
		CheckUtils.checkArgumentNotNull(template);
		this.projectTemplate = template;
		return this;
	}
	
	public ProjectGenerator withArchitecture(String architecture) {
		CheckUtils.checkArgumentNotNullOrEmpty(architecture);
		this.model.setArchitecture(architecture);
		
		return this;
	}
	
	public ProjectGenerator withLanguage(SelectedValue language) {
		this.model.setLanguageValue(language.getValue());
		this.model.setLanguageVersion(language.getVersion());
		return this;
	}
	
	public ProjectGenerator withFramework(SelectedValue framework) {
		this.model.setFrameworkValue(framework.getValue());
		this.model.setFrameworkVersion(framework.getVersion());
		return this;
	}
	
	public ProjectGenerator withNamespace(String namespace) {
		CheckUtils.checkArgumentNotNullOrEmpty(namespace);
		this.model.setNamespace(namespace);
		return this;
	}
	
	public ProjectGenerator withOrganization(String organization) {
		CheckUtils.checkArgumentNotNullOrEmpty(organization);
		this.model.setOrganization(organization);
		return this;
	}
	
	public ProjectGenerator withApiSpec(ArtifactDefinition definition) throws Exception {
		CheckUtils.checkArgumentNotNull(definition);
		this.model.setArtifact(definition);
		return this;
	}
	
	public ProjectGenerator withDatastore(SelectedValue option) {
		if (option != null) {
			this.model.setDatastoreValue(option.getValue());
			this.model.setDatastoreVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectGenerator withHttp(SelectedValue option) {
		if (option != null) {
			this.model.setHttpValue(option.getValue());
			this.model.setHttpVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectGenerator withMessaging(SelectedValue option) {
		if (option != null) {
			this.model.setMessagingValue(option.getValue());
			this.model.setMessagingVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectGenerator withDiscovery(SelectedValue option) {
		if (option != null) {
			this.model.setDiscoveryValue(option.getValue());
			this.model.setDiscoveryVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectGenerator withTracing(SelectedValue option) {
		if (option != null) {
			this.model.setTracingValue(option.getValue());
			this.model.setTracingVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectGenerator withMonitoring(SelectedValue option) {
		if (option != null) {
			this.model.setMonitoringValue(option.getValue());
			this.model.setMonitoringVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectGenerator withSecurity(SelectedValue option) {
		if (option != null) {
			this.model.setSecurityValue(option.getValue());
			this.model.setSecurityVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectGenerator withCi(SelectedValue option) {
		if (option != null) {
			this.model.setCiValue(option.getValue());
			this.model.setCiVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectGenerator withCd(SelectedValue option) {
		if (option != null) {
			this.model.setCdValue(option.getValue());
			this.model.setCdVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectGenerator withScm(SelectedValue option) {
		if (option != null) {
			this.model.setScmValue(option.getValue());
			this.model.setScmVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectGenerator withRegistry(SelectedValue option) {
		if (option != null) {
			this.model.setRegistryValue(option.getValue());
			this.model.setRegistryVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectGenerator withBuild(SelectedValue option) {
		if (option != null) {
			this.model.setBuildValue(option.getValue());
			this.model.setBuildVersion(option.getVersion());
		}
	    return this;
	}
	
	public ProjectGenerator withTest(SelectedValue option) {
		if (option != null) {
			this.model.setTestValue(option.getValue());
			this.model.setTestVersion(option.getVersion());
		}
	    return this;
	}
	
	public GeneratedProject generate() throws Exception {
		GeneratedProject project = null;
		List<GeneratedFile> files = null;
    	
        files = new ArrayList<GeneratedFile>();
        files.addAll(this.generateFiles());
        for (Directory currentDirectory : this.projectTemplate.getDirectories()) {
	        files.addAll(this.generateCode(currentDirectory.getPath()));
        }
        
        project = new GeneratedProject();
        project.setSlug(projectTemplate.getSlug());
        project.setFiles(files);
        return project;
    }
	
	public List<GeneratedFile> generateFiles() throws Exception {
		GeneratedFile generatedFile = null;
    		List<GeneratedFile> files = new ArrayList<GeneratedFile>();
		for (TemplateFile file: projectTemplate.getFiles()) {
			generatedFile = this.generateFile(null, file, "");
			if (generatedFile != null) {
    				files.add(generatedFile);   
			}
        }
		return files;
	}
	
	public List<GeneratedFile> generateCode(String type) throws Exception {
		List<GeneratedFile> generatedFiles = null;
		Directory directory = null;
		GeneratedFile generatedFile = null;
    	
	    	if (type != null) {
	    		directory = projectTemplate.getDirectoryByPath(type);
	    		generatedFiles = new ArrayList<GeneratedFile>();
	    	
	    		if (directory != null) {
			    	for (TemplateFile file: directory.getFiles()) {
						this.model.setPackageName(this.resolvePackagename());
						
					switch (directory.getResolution()) {
				        case Static:
				        		generatedFile = this.generateFile(directory, file, "");
				        		if (generatedFile != null) {
				        			generatedFiles.add(generatedFile);
				        		}
				        		break;
				        		/**
				        case Dynamic:
				        		
					        	for (PojoDefinition current : this.pojos) {
					    			this.model.setClassname(StringUtils.lowerCase(current.getName()));
					    			this.model.setSchema(current);
					    			generatedFile = this.generateFile(directory, file, current.getName());
					    			if (generatedFile != null) {
					    				generatedFiles.add(generatedFile);
					    			}
					    		}
					        	break;
					        	*/
				        default:
				        		throw new IllegalArgumentException("missing file resolution method");
			        }
			    	}
	    		}
	    	}
		return generatedFiles;
       
	}
	
	public GeneratedFile generateFile(Directory directory, TemplateFile file, String arg) throws Exception {
		GeneratedFile generatedFile = null;
		Mustache template = null;
		
		System.out.println("generating template " + file.getSlug());
		template = this.getTemplate(file.getSlug());
		
		if (this.includeFile(file)) {
			generatedFile = new GeneratedFile();
			generatedFile.setSlug(file.getSlug());
			generatedFile.setFilename(this.resolveFilename(file, arg));
			generatedFile.setPath(this.resolveOutputPath(directory));
			generatedFile.setContent(template.render(this.model));
		}
    		return generatedFile;
	}
	
	private Boolean includeFile(TemplateFile file) {
		boolean result = false;
		if (file != null) {
			if (StringUtils.isNotEmpty(file.getRule())) {
				ExpressionParser expressionParser = new SpelExpressionParser();
				Expression expression = expressionParser.parseExpression(file.getRule());
				result = expression.getValue(this.model, Boolean.class);
			} else {
				result = true;
			}
		}
		return result;
	}
	
	private Mustache getTemplate(String name) throws Exception {
		Mustache template = null;
	    	template = engine.getMustache(name);
	    	if (template == null) {
	    		throw new NotFoundException("template", name);
	    	}
	    	return template;
		
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	private String resolveFilename(TemplateFile file, String arg) {
		String resolvedName = null;
		String prefix = null;
		
		CheckUtils.checkArgumentNotNull(file);
		resolvedName = file.getName();
		
		if (StringUtils.isNotEmpty(arg)) {
			
			switch (file.getNamingStrategy()) {
				case Capitalize:
					prefix = WordUtils.capitalizeSingular(arg);
					break;
				case Lowercase:
					prefix = WordUtils.uncapitalizeSingular(arg);
					break;
				default:
					prefix = WordUtils.capitalizeSingular(arg);
			}
			
			if (StringUtils.contains(file.getName(), NAME_PLACEHOLDER)) {
				resolvedName = StringUtils.replace(file.getName(), NAME_PLACEHOLDER, prefix);
			}
		}
		
		return resolvedName;
	}
	
	private String resolveOutputPath(Directory directory) {
		String outputPath = null;
		StringBuilder basePathBuilder = new StringBuilder();
		String directoryPath = null;
		
		if (directory == null) {
			outputPath = "";
		} else {
			directoryPath = directory.getPath();
			switch (this.model.getLanguageValue()) {
				case "java":
					basePathBuilder.append(PACKAGE_PREFIX);
					basePathBuilder.append(File.separator);
					basePathBuilder.append(this.model.getOrganization());
					basePathBuilder.append(File.separator);
					basePathBuilder.append(this.model.getNamespace());
					if (!directoryPath.equals(Directory.ROOT)) {
						basePathBuilder.append(File.separator);
						basePathBuilder.append(directoryPath.toLowerCase());
					}
					break;
				default:
					if (!directoryPath.equalsIgnoreCase(Directory.ROOT)) {
						basePathBuilder.append(directoryPath.toLowerCase());
					}
					break;
			}
			outputPath = directory.getBase() + File.separator + basePathBuilder.toString();
		}
		return outputPath;
	}
	
	private String resolvePackagename() {
		StringBuilder packageNameBuilder = new StringBuilder();
		
		switch (this.model.getLanguageValue()) {
			case "java":
				packageNameBuilder.append(PACKAGE_PREFIX);
				packageNameBuilder.append(".");
				packageNameBuilder.append(this.model.getOrganization());
				packageNameBuilder.append(".");
				packageNameBuilder.append(this.model.getNamespace());
				break;
			default:
				break;
		}
		return packageNameBuilder.toString();
	}
}
