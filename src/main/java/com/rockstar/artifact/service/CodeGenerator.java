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
import com.rockstar.artifact.codegen.model.SpecDefinitions;
import com.rockstar.artifact.model.GeneratedFile;
import com.rockstar.artifact.model.GeneratedProject;
import com.rockstar.artifact.model.Model;
import com.rockstar.artifact.model.NotFoundException;
import com.rockstar.artifact.model.Project;
import com.rockstar.artifact.model.ProjectDirectory;
import com.rockstar.artifact.model.ProjectFile;

@Component
public class CodeGenerator {
	
	@Inject private MustacheEngine engine;
	@Inject private ProjectDirectory projectDirectory;
	
	public CodeGenerator(MustacheEngine engine, ProjectDirectory projectDirectory) {
		this.engine = engine;
		this.projectDirectory = projectDirectory;
	}
	
	public GeneratedProject generate(Project project) throws Exception {
		GeneratedProject generatedProject = null;
		List<GeneratedFile> allfiles = null;
		List<GeneratedFile> generatedFiles = null;
		allfiles = new ArrayList<GeneratedFile>();
		
		for (ProjectFile file : this.projectDirectory.getFiles()) {
			generatedFiles = this.generateCode(file, project);
			if (generatedFiles != null && !generatedFiles.isEmpty()) {
				allfiles.addAll(generatedFiles);
			}
        }
        
		generatedProject = new GeneratedProject();
		generatedProject.setFiles(allfiles);
        
		return generatedProject;
	}
	
	public List<GeneratedFile> generateCode(ProjectFile projectFile, Project project) throws Exception {
		List<GeneratedFile> generatedFiles = null;
		GeneratedFile generatedFile = null;
    		SpecDefinitions specDefinitions = project.getSpecDefinitions();
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
    					case Service:
    						definitions = specDefinitions.getDefinitions(Definition.Type.Service);
    						break;
    					case Search:
    						definitions = specDefinitions.getDefinitions(Definition.Type.Search);
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
	
	public GeneratedFile generateFile(ProjectFile file, Project project, Definition definition) throws Exception {
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
	
	private Boolean includeFile(ProjectFile file, Project project) {
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
	
	private Mustache getTemplate(String name) throws Exception {
		Mustache template = null;
	    	template = engine.getMustache(name);
	    	if (template == null) {
	    		throw new NotFoundException("template", name);
	    	}
	    	return template;
		
	}
	
	private String resolveFilename(ProjectFile file, Definition definition) {
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
					break;
			}
		}
		return filename;
	}
	
	private String resolveOutputPath(ProjectFile projectFile, Project project) {
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
	
	private String resolvePackageDirectory(ProjectFile projectFile, Model model) {
		ExpressionParser expressionParser = new SpelExpressionParser();
		Expression expression = expressionParser.parseExpression(projectFile.getPackageDir());
		return expression.getValue(model, String.class);
	}
	
	private String resolvePackagename(ProjectFile projectFile, Model model) {
		ExpressionParser expressionParser = new SpelExpressionParser();
		Expression expression = expressionParser.parseExpression(projectFile.getPackageDir());
		String packageDir = expression.getValue(model, String.class);
		String packageName = StringUtils.replace(packageDir, File.separator, ".");
		return packageName;
	}
}
