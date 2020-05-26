package com.rockstar.artifact.converter.openapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.reprezen.kaizen.oasparser.model3.Operation;
import com.reprezen.kaizen.oasparser.model3.Path;
import com.reprezen.kaizen.oasparser.model3.Schema;
import com.reprezen.kaizen.oasparser.model3.Tag;
import com.rockstar.artifact.codegen.model.ControllerDefinition;
import com.rockstar.artifact.codegen.model.Definition;
import com.rockstar.artifact.codegen.model.Definition.Type;
import com.rockstar.artifact.codegen.model.EntityDefinition;
import com.rockstar.artifact.codegen.model.MessagingDefinition;
import com.rockstar.artifact.codegen.model.MicroserviceDefinition;
import com.rockstar.artifact.codegen.model.DatabaseSchemaDefinition;
import com.rockstar.artifact.codegen.model.RelationshipDefinition;
import com.rockstar.artifact.codegen.model.RepositoryDefinition;
import com.rockstar.artifact.codegen.model.ResourceDefinition;
import com.rockstar.artifact.codegen.model.RestApiTestDefinition;
import com.rockstar.artifact.codegen.model.SearchDefinition;
import com.rockstar.artifact.codegen.model.ServiceDefinition;
import com.rockstar.artifact.codegen.model.UnitTestDefinition;
import com.rockstar.artifact.util.WordUtils;

@Component
public class OpenApiToRestApiMicroserviceDefinition implements Converter<OpenApi3, MicroserviceDefinition> {

	@Inject private OpenApiSchemaToResourceDefinition openApiSchemaToResourceDefinition;
	@Inject private OpenApiSchemaToEntityDefinition openApiSchemaToEntityDefinition;
	@Inject private OpenApiPathToSearchDefinition openApiPathToSearchDefinition;
	@Inject private OpenApiSchemaToDatabaseSchemaDefinition openApiSchemaToMySqlSchema;
	@Inject private OpenApiSchemaToRestApiTestDefinition openApiSchemaToApiTestDefinition;
	@Inject private OpenApiSchemaToUnitTestDefinition openApiSchemaToUnitTestDefinition;
	
	public MicroserviceDefinition convert(OpenApi3 openApi) {
		MicroserviceDefinition microserviceDefinitions = null;
		ControllerDefinition controller = null;
		ResourceDefinition resource = null;
		ServiceDefinition service = null;
		SearchDefinition search = null;
		EntityDefinition entity = null;
		Collection<RelationshipDefinition> childEntityRelationships = null;
		RepositoryDefinition repository = null;
		DatabaseSchemaDefinition mySqlSchema = null;
		MessagingDefinition messaging = null;
		RepositoryDefinition subrepository = null;
		RestApiTestDefinition apitest = null;
		UnitTestDefinition unittest = null;
		
		Map<String, Schema> schemas = null;
		Map<String, Path> pathsByTag = null;
		Collection<Tag> tags = null;
		
		Map<String, Collection<String>> resourceHierarchy = null;
		
		if (openApi != null) {
			microserviceDefinitions = new MicroserviceDefinition();
			
			Schema schema = null;
			String name = null;
			
			schemas = openApi.getSchemas();
			tags = openApi.getTags();
		
			if (tags != null && !tags.isEmpty()) {
				for (Tag currentTag : tags) {
					schema = null;
					pathsByTag = this.getPathsByTag(currentTag.getName(), openApi.getPaths());
					resourceHierarchy = this.resolvePathsToResourceHierarchy(currentTag.getName(), pathsByTag);
					
					for (Entry<String, Collection<String>> resourceEntry: resourceHierarchy.entrySet()) {
					
						schema = schemas.get(StringUtils.capitalize(resourceEntry.getKey()));
						name = resourceEntry.getKey();
						
						if (schema != null) {
							messaging = new MessagingDefinition();
							messaging.setName(name);
							microserviceDefinitions.withDefinition(Type.Messaging, messaging);
							
							apitest = this.openApiSchemaToApiTestDefinition.convert(schema);
							apitest.setName(name);
							microserviceDefinitions.withDefinition(Definition.Type.ApiTest, apitest);
							
							unittest = this.openApiSchemaToUnitTestDefinition.convert(schema);
							unittest.setName(name);
							microserviceDefinitions.withDefinition(Definition.Type.UnitTest, unittest);
						
							Operation searchOperation = this.getSearchOperation(name, pathsByTag);
							if (searchOperation != null) {
								search = this.openApiPathToSearchDefinition.convert(searchOperation);
								search.setName(name);
							}
							
							entity = this.openApiSchemaToEntityDefinition.convert(schema);
							entity.setName(name);
							
							repository = new RepositoryDefinition();
							repository.setName(name);
							repository.setUniquefields(entity.getUniquefields());
							if (search != null) {
								if (search.isPageable()) {
									repository.setPageable(true);
								}
								repository.setFilters(search.getFilters());
							}
				
							if (entity.getChildEntities() != null && !entity.getChildEntities().isEmpty()) {
								for (EntityDefinition childEntity : entity.getChildEntities()) {
									subrepository = new RepositoryDefinition();
									subrepository.setName(childEntity.getName());
									subrepository.setParent(entity.getName());
									subrepository.setUniquefields(childEntity.getUniquefields());
									childEntity.setRepository(subrepository);
									childEntityRelationships = childEntity.getRelationships();
									if (childEntityRelationships != null && !childEntityRelationships.isEmpty()) {
										for (RelationshipDefinition childEntityRelationship : childEntityRelationships) {
											if (childEntityRelationship.isManyToOne()) {
												childEntityRelationship.setParent(entity);
											}
										}
									}
								}
							}
							entity.setRepository(repository);
							
							microserviceDefinitions.withDefinition(Type.Entity, entity);
							microserviceDefinitions.withDefinition(Type.Repository, repository);
							for (EntityDefinition current : entity.getChildEntities()) {
								microserviceDefinitions.withDefinition(Type.Entity, current);
								microserviceDefinitions.withDefinition(Type.Repository, current.getRepository());
							}
							
							resource = this.openApiSchemaToResourceDefinition.convert(schema);
							resource.setName(name);
							resource.setParent(name);
							resource.setEntity(entity);
							resource.setSubresources(resourceEntry.getValue());
							microserviceDefinitions.withDefinition(Type.Resource, resource);
							
							for (String current : resource.getSubresources()) {
								Schema subresourceSchema = schemas.get(StringUtils.capitalize(current));
								ResourceDefinition subresourceDefinition = this.openApiSchemaToResourceDefinition.convert(subresourceSchema);
								subresourceDefinition.setName(current);
								subresourceDefinition.setParent(resource.getName());
								subresourceDefinition.setEntity(entity.getChildEntity(current));
								microserviceDefinitions.withDefinition(Type.Resource, subresourceDefinition);
							}
				
							service = new ServiceDefinition();
							service.setName(name);
							service.setSearch(search);
							service.setEntity(entity);
							service.setMessaging(messaging);
							microserviceDefinitions.withDefinition(Type.Service, service);
							
							controller = new ControllerDefinition();
							controller.setName(name);
							controller.setResource(resource);
							controller.setSearch(search);
							controller.setService(service);
							controller.setEntity(entity);
							microserviceDefinitions.withDefinition(Type.Controller, controller);
						} else {
							System.out.println("schema not found for resource " + resourceEntry.getKey());
						}
					}
				}
			}
			mySqlSchema = this.openApiSchemaToMySqlSchema.convert(schemas);
			microserviceDefinitions.withDefinition(Type.Schema, mySqlSchema);
		}
		return microserviceDefinitions;
	}
	
	private Map<String, Collection<String>> resolvePathsToResourceHierarchy(String tagname, Map<String, Path> paths) {
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
			String resource = WordUtils.uncapitalizeSingular(StringUtils.stripStart(currentResource, "/"));
			
			if (subresourcePaths != null && !subresourcePaths.isEmpty()) {
				
				for (String currentSubresource : subresourcePaths) {
					String subresource = WordUtils.uncapitalizeSingular(StringUtils.stripStart(currentSubresource, "/"));
					if (StringUtils.isNotEmpty(subresource)) {
						resources.put(resource, subresource);
					}
				}
			}
		}
		
		return resources.asMap();
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
	
	private Operation getSearchOperation(String resource, Map<String, Path> paths) {
		Path currentPath = null;
		Operation currentOperation = null;
		
		for (Entry<String, Path> pathEntry: paths.entrySet()) {
			if (pathEntry.getKey().equalsIgnoreCase("/" + StringUtils.lowerCase(resource))) {
				currentPath = pathEntry.getValue();
				currentOperation = currentPath.getOperation("get");
				if (currentOperation != null) {
					break;
				}
			}
		}
		
		return currentOperation;
	}
	
}
