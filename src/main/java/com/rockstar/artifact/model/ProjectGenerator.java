package com.rockstar.artifact.model;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.trimou.Mustache;
import org.trimou.engine.MustacheEngine;

import com.reprezen.kaizen.oasparser.OpenApi3Parser;
import com.reprezen.kaizen.oasparser.model3.Contact;
import com.reprezen.kaizen.oasparser.model3.Info;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.reprezen.kaizen.oasparser.val.ValidationResults.ValidationItem;
import com.rockstar.artifact.util.CheckUtils;
import com.rockstar.artifact.util.WordUtils;

public class ProjectGenerator {
	
	private static final String PACKAGE_PREFIX = "com";
	private static final String PACKAGE_PLACEHOLDER = "com.{organization}.{namespace}";
	private static final String NAME_PLACEHOLDER = "{name}";
	
	private OpenApi3 spec;
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
	
	public ProjectGenerator withType(String type) {
		CheckUtils.checkArgumentNotNullOrEmpty(type);
		this.model.setType(type);
		
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
	
	public ProjectGenerator withSpec(String spec) throws Exception {
		Contact contact = null;
		Info info = null;
		
		OpenApi3 openapi3Model = new OpenApi3Parser().parse(new URI(spec), true);
		if (!openapi3Model.isValid()) {
			for (ValidationItem item : openapi3Model.getValidationItems()) {
				throw new InvalidSchemaException(item.getMsg());
			}
		} else {
			this.spec = openapi3Model;
			info = this.spec.getInfo();
			if (info != null) {
				this.model.setVersion(info.getVersion());
				contact = info.getContact();
				if (contact != null) {
					this.model.setContact(contact.getEmail());
				}
			}
		}
    		
		return this;
	}
	
	public ProjectGenerator withDatastore(SelectedValue option) {
		this.model.setDatastoreValue(option.getValue());
	    this.model.setDatastoreVersion(option.getVersion());
	    return this;
	}
	
	public ProjectGenerator withHttp(SelectedValue option) {
		this.model.setHttpValue(option.getValue());
	    this.model.setHttpVersion(option.getVersion());
	    return this;
	}
	
	public ProjectGenerator withMessaging(SelectedValue option) {
		this.model.setMessagingValue(option.getValue());
	    this.model.setMessagingVersion(option.getVersion());
	    return this;
	}
	
	public ProjectGenerator withDiscovery(SelectedValue option) {
		this.model.setDiscoveryValue(option.getValue());
	    this.model.setDiscoveryVersion(option.getVersion());
	    return this;
	}
	
	public ProjectGenerator withTracing(SelectedValue option) {
		this.model.setTracingValue(option.getValue());
	    this.model.setTracingVersion(option.getVersion());
	    return this;
	}
	
	public ProjectGenerator withMonitoring(SelectedValue option) {
		this.model.setMonitoringValue(option.getValue());
	    this.model.setMonitoringVersion(option.getVersion());
	    return this;
	}
	
	public ProjectGenerator withSecurity(SelectedValue option) {
		this.model.setSecurityValue(option.getValue());
	    this.model.setSecurityVersion(option.getVersion());
	    return this;
	}
	
	public ProjectGenerator withCi(SelectedValue option) {
		this.model.setCiValue(option.getValue());
	    this.model.setCiVersion(option.getVersion());
	    return this;
	}
	
	public ProjectGenerator withCd(SelectedValue option) {
		this.model.setCdValue(option.getValue());
	    this.model.setCdVersion(option.getVersion());
	    return this;
	}
	
	public ProjectGenerator withScm(SelectedValue option) {
		this.model.setScmValue(option.getValue());
	    this.model.setScmVersion(option.getVersion());
	    return this;
	}
	
	public ProjectGenerator withRegistry(SelectedValue option) {
		this.model.setRegistryValue(option.getValue());
	    this.model.setRegistryVersion(option.getVersion());
	    return this;
	}
	
	public ProjectGenerator withBuild(SelectedValue option) {
		this.model.setBuildValue(option.getValue());
	    this.model.setBuildVersion(option.getVersion());
	    return this;
	}
	
	public ProjectGenerator withTest(SelectedValue option) {
		this.model.setTestValue(option.getValue());
	    this.model.setTestVersion(option.getVersion());
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
    		List<GeneratedFile> files = new ArrayList<GeneratedFile>();
		for (TemplateFile file: projectTemplate.getFiles()) {
    			files.add(this.generateFile(null, file, ""));   
        }
		return files;
	}
	
	public List<GeneratedFile> generateCode(String type) throws Exception {
    	List<GeneratedFile> generatedFiles = null;
    	Directory directory = null;
    	
    	if (type != null) {
    		directory = projectTemplate.getDirectoryByPath(type);
    		generatedFiles = new ArrayList<GeneratedFile>();
    	
    		if (directory != null) {
		    	for (TemplateFile file: directory.getFiles()) {
					this.model.setPackageName(this.resolvePackagename());
					
				switch (directory.getResolution()) {
			        case Static:
			        	generatedFiles.add(this.generateFile(directory, file, ""));
			        	break;
			        case Dynamic:
			        	for (String current : this.spec.getSchemas().keySet()) {
			    			this.model.setClassname(StringUtils.lowerCase(current));
			    			this.model.setSchema(this.spec.getSchema(current));
				        	generatedFiles.add(this.generateFile(directory, file, current));
			    		}
			        	break;
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
		
		template = this.getTemplate(file.getSlug());
		generatedFile = new GeneratedFile();
		generatedFile.setSlug(file.getSlug());
		generatedFile.setFilename(this.resolveFilename(file, arg));
		generatedFile.setPath(this.resolveOutputPath(directory));
		generatedFile.setContent(template.render(this.model));
    	
    		return generatedFile;
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
