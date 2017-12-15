package com.ibmcloud.kickster.model;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.trimou.Mustache;
import org.trimou.engine.MustacheEngine;

import com.ibmcloud.kickster.util.CheckUtils;
import com.ibmcloud.kickster.util.WordUtils;
import com.reprezen.kaizen.oasparser.OpenApi3Parser;
import com.reprezen.kaizen.oasparser.model3.Contact;
import com.reprezen.kaizen.oasparser.model3.Info;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.reprezen.kaizen.oasparser.val.ValidationResults.ValidationItem;

public class ProjectGenerator {
	
	private static final String PACKAGE_PREFIX = "com";
	private static final String PACKAGE_PLACEHOLDER = "com.{organization}.{namespace}";
	private static final String NAME_PLACEHOLDER = "{name}";
	
	private OpenApi3 spec;
	private String language;
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
	
	public ProjectGenerator withLanguage(String language) {
		CheckUtils.checkArgumentNotNullOrEmpty(language);
		this.language = language;
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
	
	public ProjectGenerator withOptions(Map<String, String> options) {
		this.model.setOptions(options);
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
		
		if (StringUtils.isNotEmpty(this.language) && StringUtils.isNotEmpty(arg)) {
			
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
			switch (this.language) {
				case "java8":
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
		
		switch (this.language) {
			case "java8":
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
