package com.rockstar.artifact.service;

import com.rockstar.artifact.web.ArtifactResource;

public interface ArtifactService {

	public byte[] getArtifact(String artifactId) throws Exception;
	public String createArtifact(ArtifactResource artifact) throws Exception;
	
}
