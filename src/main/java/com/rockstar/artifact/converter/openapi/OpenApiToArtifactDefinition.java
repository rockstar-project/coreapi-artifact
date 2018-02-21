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

import com.reprezen.kaizen.oasparser.jsonoverlay.Reference;
import com.reprezen.kaizen.oasparser.model3.Info;
import com.reprezen.kaizen.oasparser.model3.MediaType;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.reprezen.kaizen.oasparser.model3.Operation;
import com.reprezen.kaizen.oasparser.model3.Path;
import com.reprezen.kaizen.oasparser.model3.Response;
import com.reprezen.kaizen.oasparser.model3.Schema;
import com.reprezen.kaizen.oasparser.model3.SecurityRequirement;
import com.reprezen.kaizen.oasparser.model3.Tag;
import com.rockstar.artifact.codegen.model.ArtifactDefinition;
import com.rockstar.artifact.codegen.model.AttributeDefinition;
import com.rockstar.artifact.codegen.model.ConstraintDefinition;
import com.rockstar.artifact.codegen.model.ConstraintType;
import com.rockstar.artifact.codegen.model.ControllerDefinition;
import com.rockstar.artifact.codegen.model.GeneralDefinition;
import com.rockstar.artifact.codegen.model.ResourceDefinition;
import com.rockstar.artifact.codegen.model.SearchDefinition;
import com.rockstar.artifact.codegen.model.SecurityDefinition;
import com.rockstar.artifact.codegen.model.ServiceDefinition;
import com.rockstar.artifact.codegen.model.WebDefinition;
import com.rockstar.artifact.util.WordUtils;

@Component
public class OpenApiToArtifactDefinition implements Converter<OpenApi3, ArtifactDefinition> {

	@Inject private OpenApiInfoToGeneralDefinition openApiInfoToGeneralDefinition;
	@Inject private OpenApiSchemaToResourceDefinition openApiSchemaToResourceDefinition;
	@Inject private OpenApiPathToSearchDefinition openApiPathToSearchDefinition;
	@Inject private OpenApiSchemaToServiceDefinition openApiSchemaToServiceDefinition;
	@Inject private OpenApiSecurityRequirementsToSecurityConfigDefinition openApiSecurityRequirementsToSecurityDefinition;
	
	public ArtifactDefinition convert(OpenApi3 openApi) {
		ArtifactDefinition restapiDefinition = null;
		WebDefinition webDefinition = null;
		GeneralDefinition generalDefinition = null;
		Collection<ControllerDefinition> controllerDefinitions = null;
		ControllerDefinition controllerDefinition = null;
		ResourceDefinition resourceDefinition = null;
		ServiceDefinition serviceDefinition = null;
		SearchDefinition searchDefinition = null;
		SecurityDefinition securityDefinition = null;
		
		Info info = null;
		Map<String, Schema> schemas = null;
		Map<String, Path> pathsByTag = null;
		Collection<Tag> tags = null;
		Collection<SecurityRequirement> securityRequirements = null;
		
		if (openApi != null) {
			restapiDefinition = new ArtifactDefinition();
			
			info = openApi.getInfo();
			if (info != null) {
				generalDefinition = this.openApiInfoToGeneralDefinition.convert(info);
				restapiDefinition.setGeneral(generalDefinition);
			}
			
			String schemaKey = null;
			Schema schema = null;
			Reference schemaReference = null;
			String name = null;
			
			schemas = openApi.getSchemas();
			tags = openApi.getTags();
			if (tags != null && !tags.isEmpty()) {
				controllerDefinitions = new ArrayList<ControllerDefinition> ();
				for (Tag currentTag : tags) {
					pathsByTag = this.getPathsByTagname(currentTag.getName(), openApi.getPaths());
					schemaReference = this.getSchemaReference(pathsByTag);
					if (schemaReference != null) {
						schemaKey = StringUtils.substringAfterLast(schemaReference.getKey(), "/");
						schema = schemas.get(schemaKey);
						name = WordUtils.uncapitalizeSingular(schemaKey);
						
						resourceDefinition = this.openApiSchemaToResourceDefinition.convert(schema);
						resourceDefinition.setName(name);
						this.setRequiredFields(resourceDefinition, schema.getRequiredFields());

						searchDefinition = this.openApiPathToSearchDefinition.convert(pathsByTag);
						
						serviceDefinition = this.openApiSchemaToServiceDefinition.convert(schema);
						serviceDefinition.setName(name);
						serviceDefinition.setSearch(searchDefinition);
						
						controllerDefinition = new ControllerDefinition();
						controllerDefinition.setResource(resourceDefinition);
						controllerDefinition.setSearch(searchDefinition);
						controllerDefinition.setService(serviceDefinition);
						controllerDefinitions.add(controllerDefinition);
					}
				}
				webDefinition = new WebDefinition();
				webDefinition.setControllers(controllerDefinitions);
				restapiDefinition.setWeb(webDefinition);
			}
			
			securityRequirements = openApi.getSecurityRequirements();
			if (securityRequirements != null && !securityRequirements.isEmpty()) {
				securityDefinition = this.openApiSecurityRequirementsToSecurityDefinition.convert(securityRequirements);
				restapiDefinition.setSecurity(securityDefinition);
			}
			
		}
		return restapiDefinition;
	}
	
	private Map<String, Path> getPathsByTagname(String tagname, Map<String, Path> paths) {
		Map<String, Path> taggedPaths = new HashMap<String, Path>();
		
		for (Entry<String, Path> pathEntry: paths.entrySet()) {
			if (pathEntry.getKey().startsWith("/" + StringUtils.lowerCase(tagname))) {
				taggedPaths.put(pathEntry.getKey(), pathEntry.getValue());
			}
		}
		
		return taggedPaths;
	}
	
	private Reference getSchemaReference(Map<String, Path> paths) {
		Path path = null;
		Iterator<Path> pathIterator = null;
		Operation getOperation = null;
		Response getOperationResponse = null;
		MediaType responseJsonContent = null;
		Schema responseSchema = null;
		Reference schemaReference = null;
		
		pathIterator = paths.values().iterator();
		while (pathIterator.hasNext()) {
			path = pathIterator.next();
			getOperation = path.getOperation("get");
			getOperationResponse = getOperation.getResponse("200");
			responseJsonContent = getOperationResponse.getContentMediaType("application/json");
			responseSchema = responseJsonContent.getSchema();
			schemaReference = responseSchema.getItemsSchemaReference();
		}
		
		return schemaReference;
	}
	
	private void setRequiredFields(ResourceDefinition resourceDefinition, Collection<String> requiredfields) {
		AttributeDefinition attributeDefinition = null;
		ConstraintDefinition constraints = null;
		for (String field : requiredfields) {
			attributeDefinition = resourceDefinition.getAttributeByName(field);
			constraints = attributeDefinition.getConstraints();
			if (constraints != null) {
				constraints.addConstraint(ConstraintType.NotEmpty);
			}
		}
	}
	
	
}
