package com.rockstar.artifact.converter.openapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.reprezen.kaizen.oasparser.jsonoverlay.Reference;
import com.reprezen.kaizen.oasparser.model3.Info;
import com.reprezen.kaizen.oasparser.model3.MediaType;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.reprezen.kaizen.oasparser.model3.Operation;
import com.reprezen.kaizen.oasparser.model3.Path;
import com.reprezen.kaizen.oasparser.model3.Response;
import com.reprezen.kaizen.oasparser.model3.Schema;
import com.reprezen.kaizen.oasparser.model3.Tag;
import com.rockstar.artifact.codegen.model.ControllerDefinition;
import com.rockstar.artifact.codegen.model.Definition.Type;
import com.rockstar.artifact.codegen.model.EntityDefinition;
import com.rockstar.artifact.codegen.model.MySqlSchemaDefinition;
import com.rockstar.artifact.codegen.model.RepositoryDefinition;
import com.rockstar.artifact.codegen.model.ResourceDefinition;
import com.rockstar.artifact.codegen.model.SearchDefinition;
import com.rockstar.artifact.codegen.model.ServiceDefinition;
import com.rockstar.artifact.codegen.model.SpecDefinitions;
import com.rockstar.artifact.util.WordUtils;

@Component
public class OpenApiToSpecDefinitions implements Converter<OpenApi3, SpecDefinitions> {

	@Inject private OpenApiInfoToGeneralDefinition openApiInfoToGeneralDefinition;
	@Inject private OpenApiSchemaToResourceDefinition openApiSchemaToResourceDefinition;
	@Inject private OpenApiSchemaToEntityDefinition openApiSchemaToEntityDefinition;
	@Inject private OpenApiPathToSearchDefinition openApiPathToSearchDefinition;
	@Inject private OpenApiSchemaToMySqlSchemaDefinition openApiSchemaToMySqlSchema;
	
	public SpecDefinitions convert(OpenApi3 openApi) {
		SpecDefinitions specDefinitions = null;
		ControllerDefinition controller = null;
		ResourceDefinition resource = null;
		ServiceDefinition service = null;
		SearchDefinition search = null;
		EntityDefinition entity = null;
		RepositoryDefinition repository = null;
		MySqlSchemaDefinition mySqlSchema = null;
		
		Info info = null;
		Map<String, Schema> schemas = null;
		Map<String, Path> pathsByTag = null;
		Collection<Tag> tags = null;
		
		Map<String, Collection<String>> componentsHierarchy = null;
		
		if (openApi != null) {
			specDefinitions = new SpecDefinitions();
			
			info = openApi.getInfo();
			if (info != null) {
				specDefinitions.withDefinition(Type.General, this.openApiInfoToGeneralDefinition.convert(info));
			}
			
			String schemaKey = null;
			Schema schema = null;
			String name = null;
			
			schemas = openApi.getSchemas();
			tags = openApi.getTags();
		
			if (tags != null && !tags.isEmpty()) {
				for (Tag currentTag : tags) {
					schema = null;
					componentsHierarchy = this.resolvePathsToComponentsHierarchy(currentTag.getName(), openApi.getPaths());
					System.out.println(this.printComponentsHierachy(componentsHierarchy));
					
					pathsByTag = this.getPathsByTag(currentTag.getName(), openApi.getPaths());
				
					schemaKey = this.GETSuccessResponseSchemaKey(pathsByTag);
					System.out.println("Schema Key: " + schemaKey);
					if (!StringUtils.isEmpty(schemaKey)) {
						schema = schemas.get(schemaKey);
						name = WordUtils.uncapitalizeSingular(schemaKey);
					} 
					
					if (schema != null) {
						entity = this.openApiSchemaToEntityDefinition.convert(schema);
						entity.setName(name);
						
						search = this.openApiPathToSearchDefinition.convert(pathsByTag);
						search.setName(name);
						
						resource = this.openApiSchemaToResourceDefinition.convert(schema);
						resource.setName(name);
						resource.setEntity(entity);
						
						repository = new RepositoryDefinition();
						repository.setName(name);
						repository.setUniquefields(entity.getUniquefields());
						
						service = new ServiceDefinition();
						service.setName(name);
						service.setSearch(search);
						service.setEntity(entity);
						service.setRepository(repository);
						
						controller = new ControllerDefinition();
						controller.setName(name);
						controller.setResource(resource);
						controller.setSearch(search);
						controller.setService(service);
						controller.setEntity(entity);
						
						specDefinitions.withDefinition(Type.Resource, resource);
						specDefinitions.withDefinition(Type.Service, service);
						specDefinitions.withDefinition(Type.Entity, entity);
						specDefinitions.withDefinition(Type.Repository, repository);
						specDefinitions.withDefinition(Type.Search, search);
						specDefinitions.withDefinition(Type.Controller, controller);
						
						System.out.println(controller);
					}
				}
			}
			mySqlSchema = this.openApiSchemaToMySqlSchema.convert(schemas);
			specDefinitions.withDefinition(Type.MySqlSchema, mySqlSchema);
		}
		return specDefinitions;
	}
	
	private Map<String, Collection<String>> resolvePathsToComponentsHierarchy(String tagname, Map<String, Path> paths) {
		Collection<String> uriStrings = null;
		Collection<String> resourcePaths = null;
		Collection<String> subresourcePaths = null;
	
		resourcePaths = new ArrayList<String> ();
		uriStrings = this.getPathUris(tagname, paths);
		
		for (String current: uriStrings) {
			if (current.indexOf("/") == current.lastIndexOf("/")) {
				if (!resourcePaths.contains(current)) {
					resourcePaths.add(current);
				}
			}
		}
		
		for (String currentResource : resourcePaths) {
			subresourcePaths = new ArrayList<String> ();
			
			for (String currentUri: uriStrings) {
				if (currentUri.startsWith(currentResource + "/{id}")) {
					String uri = StringUtils.substringAfter(currentUri, currentResource + "/{id}");
					if (uri.indexOf("/") == uri.lastIndexOf("/")) {
						subresourcePaths.add(uri);
					}
				}
			}
		}
			
		Multimap<String, String> resources = ArrayListMultimap.create();
		for (String currentResource : resourcePaths) {
			String resource = WordUtils.capitalizeSingular(StringUtils.stripStart(currentResource, "/"));
			
			if (subresourcePaths != null && !subresourcePaths.isEmpty()) {
				
				for (String currentSubresource : subresourcePaths) {
					String subresource = WordUtils.capitalizeSingular(StringUtils.stripStart(currentSubresource, "/"));
					resources.put(resource, subresource);
				}
			}
		}
		
		return resources.asMap();
	}
	
	private String printComponentsHierachy(Map<String, Collection<String>> componentsHierachy) {
		StringBuilder componentHierarchyStringBuilder = new StringBuilder();
		if (componentsHierachy != null && !componentsHierachy.isEmpty()) {
			for (Entry<String, Collection<String>> currentEntry : componentsHierachy.entrySet()) {
				componentHierarchyStringBuilder.append(currentEntry.getKey() + " =>");
				for (String current : currentEntry.getValue()) {
					componentHierarchyStringBuilder.append(" " + current);
				}
				componentHierarchyStringBuilder.append("\n");
			}
		}
		return componentHierarchyStringBuilder.toString();
	}
	
	private Collection<String> getPathUris(String tagname, Map<String, Path> paths) {
		Collection<String> uris = new ArrayList<String> ();
		for (Entry<String, Path> pathEntry: paths.entrySet()) {
			if (pathEntry.getKey().startsWith("/" + StringUtils.lowerCase(tagname))) {
				uris.add(pathEntry.getKey());
			}
		}
		return uris;
	}
	
	private Map<String, Path> getPathsByTag(String tagname, Map<String, Path> paths) {
		Map<String, Path> taggedPaths = new HashMap<String, Path>();
		
		for (Entry<String, Path> pathEntry: paths.entrySet()) {
			if (pathEntry.getKey().startsWith("/" + StringUtils.lowerCase(tagname))) {
				taggedPaths.put(pathEntry.getKey(), pathEntry.getValue());
			}
		}
		
		return taggedPaths;
	}
	
	
	private String GETSuccessResponseSchemaKey(Map<String, Path> paths) {
		Path path = null;
		Iterator<Path> pathIterator = null;
		Operation getOperation = null;
		Response getOperationResponse = null;
		MediaType responseJsonContent = null;
		Reference responseSchemaReference = null;
		String schemaKey = null;
		
		pathIterator = paths.values().iterator();
		while (pathIterator.hasNext()) {
			path = pathIterator.next();
			getOperation = path.getOperation("get");
			getOperationResponse = getOperation.getResponse("200");
			responseJsonContent = getOperationResponse.getContentMediaType("application/json");
			responseSchemaReference = responseJsonContent.getSchemaReference();
			
			if (responseSchemaReference != null) {
				schemaKey = StringUtils.substringAfterLast(responseSchemaReference.getKey(), "/");
			}
		}
		
		return schemaKey;
	}
	
	private Schema GETSuccessResponseSchema(Map<String, Path> paths) {
		Path path = null;
		Iterator<Path> pathIterator = null;
		Operation getOperation = null;
		Response getOperationResponse = null;
		MediaType responseJsonContent = null;
		Schema responseSchema = null;
		
		pathIterator = paths.values().iterator();
		while (pathIterator.hasNext()) {
			path = pathIterator.next();
			getOperation = path.getOperation("get");
			getOperationResponse = getOperation.getResponse("200");
			responseJsonContent = getOperationResponse.getContentMediaType("application/json");
			responseSchema = responseJsonContent.getSchema();
		}
		
		return responseSchema;
	}
	
	
}
