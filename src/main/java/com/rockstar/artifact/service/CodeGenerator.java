package com.rockstar.artifact.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;
import org.trimou.Mustache;
import org.trimou.engine.MustacheEngine;

import com.rockstar.artifact.codegen.model.Definition;
import com.rockstar.artifact.codegen.model.MicroserviceDefinition;
import com.rockstar.artifact.model.GeneratedFile;
import com.rockstar.artifact.model.GeneratedProject;
import com.rockstar.artifact.model.Model;
import com.rockstar.artifact.model.Project;
import com.rockstar.artifact.model.Template;
import com.rockstar.artifact.model.TemplateDirectory;
import com.rockstar.artifact.model.TemplateRegistry;

@Component
public class CodeGenerator {
	
	@Inject private MustacheEngine engine;
	@Inject TemplateRegistry templateRegistry;
	
	public CodeGenerator(MustacheEngine engine, TemplateRegistry projectTemplateRegistry) {
		this.engine = engine;
		this.templateRegistry = projectTemplateRegistry;
	}
	
	public GeneratedProject generate(Project project) throws CodeGenerationException {
		GeneratedProject generatedProject = null;
		List<GeneratedFile> allfiles = null;
		List<GeneratedFile> generatedFiles = null;
		TemplateDirectory projectTemplate = null;
		Model projectModel = null;
		Collection<Template> projectFiles = null;
		
		if (project != null) {
			projectModel = project.getModel();
			if (projectModel != null) {
				projectTemplate = this.templateRegistry.lookup(String.format("%s-%s-%s", projectModel.getArchitecture(), projectModel.getLanguageValue(), projectModel.getFrameworkValue()));
				
				projectFiles = projectTemplate.getFiles();
				if (projectFiles != null && !projectFiles.isEmpty()) {
					allfiles = new ArrayList<GeneratedFile>();
					
					for (Template file : projectFiles) {
						generatedFiles = this.generateCode(file, project);
						if (generatedFiles != null && !generatedFiles.isEmpty()) {
							allfiles.addAll(generatedFiles);
						}
			        }
			
					generatedProject = new GeneratedProject();
					generatedProject.setFiles(allfiles);
				}
			}
		}
		
		return generatedProject;
	}
	
	private List<GeneratedFile> generateCode(Template projectFile, Project project) throws CodeGenerationException {
		List<GeneratedFile> generatedFiles = null;
		GeneratedFile generatedFile = null;
    		MicroserviceDefinition specDefinitions = project.getSpecDefinitions();
    		generatedFiles = new ArrayList<GeneratedFile>();
    		
    		if (this.includeFile(projectFile, project)) {
    			project.getModel().setPackageName(this.resolvePackagename(projectFile, project.getModel()));
    			if (!StringUtils.isEmpty(projectFile.getComponent())) {
    			
    				Collection<Definition> definitions = null;
    				Definition.Type definitionType = Definition.Type.valueOf(StringUtils.capitalize(projectFile.getComponent()));
    				switch (definitionType) {
    					case Controller:
    						definitions = specDefinitions.getDefinitions(Definition.Type.Controller);
    						break;
    					case Resource:
    						definitions = specDefinitions.getDefinitions(Definition.Type.Resource);
    						break;
    					case Model:
    						definitions = specDefinitions.getDefinitions(Definition.Type.Model);
    						break;
    					case Service:
    						definitions = specDefinitions.getDefinitions(Definition.Type.Service);
    						break;
    					case Entity:
    						definitions = specDefinitions.getDefinitions(Definition.Type.Entity);
    						break;
    					case Repository:
    						definitions = specDefinitions.getDefinitions(Definition.Type.Repository);
    						break;
    					case MySqlSchema:
    						definitions = specDefinitions.getDefinitions(Definition.Type.MySqlSchema);
    						break;
    					case Messaging:
    						definitions = specDefinitions.getDefinitions(Definition.Type.Messaging);
    						break;
    					default:
    						break;
    				}
    				
    				if (definitions != null && !definitions.isEmpty()) {
        				for (Definition current : definitions) {
			    			project.getModel().setName(StringUtils.lowerCase(current.getName()));
			    			project.getModel().setDefinition(current);
			    			generatedFile = this.generateFile(projectFile, project, current);
			    			if (generatedFile != null) {
			    				generatedFiles.add(generatedFile);
			    			}
			    		}
    				} 
    			} else {
				generatedFile = this.generateFile(projectFile, project, null);
				if (generatedFile != null) {
					generatedFiles.add(generatedFile);
				}
			}
    		}
		
	    	return generatedFiles;
	 
	}
	
	private GeneratedFile generateFile(Template file, Project project, Definition definition) throws CodeGenerationException {
		GeneratedFile generatedFile = null;
		Mustache template = null;
		
		System.out.println("generating template " + file.getTemplate());
		
		template = this.getTemplate(file.getTemplate());
		
		generatedFile = new GeneratedFile();
		generatedFile.setSlug(file.getTemplate());
		generatedFile.setFilename(this.resolveFilename(file, definition));
		generatedFile.setPath(this.resolveOutputPath(file, project));
		generatedFile.setContent(template.render(project.getModel()));
		
    		return generatedFile;
	}
	
	private Boolean includeFile(Template file, Project project) {
		boolean result = false;
		if (file != null) {
			if (StringUtils.isNotEmpty(file.getRule())) {
				ExpressionParser expressionParser = new SpelExpressionParser();
				Expression expression = expressionParser.parseExpression(file.getRule());
				result = expression.getValue(project.getModel(), Boolean.class);
			} else {
				result = true;
			}
		}
		return result;
	}
	
	private Mustache getTemplate(String name) throws CodeGenerationException {
		Mustache template = null;
	    	template = engine.getMustache(name);
	    	if (template == null) {
	    		throw new CodeGenerationException(String.format("template %s not found", name));
	    	}
	    	return template;
		
	}
	
	private String resolveFilename(Template file, Definition definition) throws CodeGenerationException {
		String filename = null;

		if (definition != null) {
			ExpressionParser expressionParser = new SpelExpressionParser();
			Expression expression = expressionParser.parseExpression(file.getFilename());
			filename = expression.getValue(definition, String.class);
		} else {
			filename = file.getFilename();
		}
		if (StringUtils.isNotEmpty(file.getNamingConvention())) {
			switch (file.getNamingConvention()) {
				case "capitalize":
					filename = StringUtils.capitalize(filename);
					break;
				case "lowercase":
					filename = StringUtils.lowerCase(filename);
					break;
				case "uppercase":
					filename = StringUtils.upperCase(filename);
					break;
				default:
					throw new CodeGenerationException("unable to resolve filename. missing file naming strategy");
			}
		}
		return filename;
	}
	
	private String resolveOutputPath(Template projectFile, Project project) {
		StringBuilder outputPathBuilder = new StringBuilder();
		
		outputPathBuilder.append(project.getModel().getArchitecture());
		outputPathBuilder.append("-");
		outputPathBuilder.append(project.getModel().getNamespace());
		outputPathBuilder.append(File.separator);
		if (StringUtils.isNotEmpty(projectFile.getBaseDir())) {
			outputPathBuilder.append(projectFile.getBaseDir());
			if (StringUtils.isNotEmpty(projectFile.getPackageDir())) {
				outputPathBuilder.append(File.separator);
				outputPathBuilder.append(this.resolvePackageDirectory(projectFile, project.getModel()));
				outputPathBuilder.append(File.separator);
			} 
		}
		return outputPathBuilder.toString();
	}
	
	private String resolvePackageDirectory(Template projectFile, Model model) {
		ExpressionParser expressionParser = new SpelExpressionParser();
		Expression expression = expressionParser.parseExpression(projectFile.getPackageDir());
		return expression.getValue(model, String.class);
	}
	
	private String resolvePackagename(Template projectFile, Model model) {
		ExpressionParser expressionParser = new SpelExpressionParser();
		Expression expression = expressionParser.parseExpression(projectFile.getPackageDir());
		String packageDir = expression.getValue(model, String.class);
		String packageName = StringUtils.replace(packageDir, File.separator, ".");
		return packageName;
	}
}
