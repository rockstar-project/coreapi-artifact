package com.rockstar.artifact.converter.openapi;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.reprezen.kaizen.oasparser.jsonoverlay.Reference;
import com.reprezen.kaizen.oasparser.model3.Info;
import com.reprezen.kaizen.oasparser.model3.MediaType;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.reprezen.kaizen.oasparser.model3.Operation;
import com.reprezen.kaizen.oasparser.model3.Path;
import com.reprezen.kaizen.oasparser.model3.Response;
import com.reprezen.kaizen.oasparser.model3.Schema;
import com.reprezen.kaizen.oasparser.model3.Tag;
import com.rockstar.artifact.codegen.model.AttributeDefinition;
import com.rockstar.artifact.codegen.model.ConstraintType;
import com.rockstar.artifact.codegen.model.ControllerDefinition;
import com.rockstar.artifact.codegen.model.Definition.Type;
import com.rockstar.artifact.codegen.model.EntityDefinition;
import com.rockstar.artifact.codegen.model.RepositoryDefinition;
import com.rockstar.artifact.codegen.model.ResourceDefinition;
import com.rockstar.artifact.codegen.model.SearchDefinition;
import com.rockstar.artifact.codegen.model.ServiceDefinition;
import com.rockstar.artifact.codegen.model.SpecDefinitions;
import com.rockstar.artifact.util.WordUtils;

@Component
public class OpenApiToSpecDefinitions implements Converter<OpenApi3, SpecDefinitions> {

	@Inject private OpenApiInfoToGeneralDefinition openApiInfoToGeneralDefinition;
	@Inject private OpenApiSchemaToAttributeDefinitionCollection openApiSchemaToAttributeDefinitions;
	@Inject private OpenApiSchemaToEntityDefinition openApiSchemaToEntityDefinition;
	@Inject private OpenApiPathToSearchDefinition openApiPathToSearchDefinition;
	
	public SpecDefinitions convert(OpenApi3 openApi) {
		SpecDefinitions specDefinitions = null;
		ControllerDefinition controller = null;
		ResourceDefinition resource = null;
		Collection<AttributeDefinition> attributes;
		ServiceDefinition service = null;
		SearchDefinition search = null;
		EntityDefinition entity = null;
		RepositoryDefinition repository = null;
		
		Info info = null;
		Map<String, Schema> schemas = null;
		Map<String, Path> pathsByTag = null;
		Collection<Tag> tags = null;
		
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
					pathsByTag = this.getPathsByTag(currentTag.getName(), openApi.getPaths());
					
					schemaKey = this.GETSuccessResponseSchemaKey(pathsByTag);
					if (!StringUtils.isEmpty(schemaKey)) {
						schema = schemas.get(schemaKey);
						name = WordUtils.uncapitalizeSingular(schemaKey);
					} else {
						schema = this.GETSuccessResponseSchema(pathsByTag);
						name = WordUtils.uncapitalizeSingular(currentTag.getName());
					}
					
					if (schema != null) {
						attributes = this.openApiSchemaToAttributeDefinitions.convert(schema);
						this.setRequiredFields(attributes, schema.getRequiredFields());
						
						resource = new ResourceDefinition();
						resource.setName(name);
						resource.setAttributes(attributes);
						
						search = this.openApiPathToSearchDefinition.convert(pathsByTag);
						search.setName(name);
						
						entity = this.openApiSchemaToEntityDefinition.convert(schema);
						entity.setName(name);
						
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
		}
		return specDefinitions;
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
	
	private void setRequiredFields(Collection<AttributeDefinition> attributes, Collection<String> requiredfields) {
		AttributeDefinition attributeDefinition = null;
		for (String field : requiredfields) {
			attributeDefinition = this.findAttributeByName(attributes, field);
			attributeDefinition.addConstraint(ConstraintType.NotEmpty);
		}
	}
	
	private AttributeDefinition findAttributeByName(Collection<AttributeDefinition> attributes, String name) {
		AttributeDefinition attributeDefinition = null;
		for (AttributeDefinition currentDefinition : attributes) {
			if (currentDefinition.getName().equalsIgnoreCase(name)) {
				attributeDefinition = currentDefinition;
				break;
			}
		}
		return attributeDefinition;
	}
	
	
}
