package com.rockstar.artifact.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rockstar.artifact.service.ArtifactService;

@RestController
@RequestMapping("/artifacts")
@ExposesResourceFor(ArtifactResource.class)
public class ArtifactController {
	
	@Inject private ArtifactService artifactService;
	
	@RequestMapping(method=RequestMethod.POST )
	public ResponseEntity<Void> createArtifact(@RequestBody @Valid ArtifactResource  artifact) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		System.out.println(ReflectionToStringBuilder.toString(artifact));
		headers.setLocation(linkTo(ArtifactController.class).slash(this.artifactService.createArtifact(artifact)).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET, produces = { "application/zip" } )
	public ResponseEntity<byte[]> getArtifact(@PathVariable("id") String artifactId) throws Exception {
		byte[] artifactContent = this.artifactService.getArtifact(artifactId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", "kickster-artifact.zip");
        headers.setContentLength(artifactContent.length);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        
        return new ResponseEntity<byte[]>(artifactContent, headers, HttpStatus.OK);
	}
	
	
}