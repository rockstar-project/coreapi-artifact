package com.ibmcloud.kickster.service;

import com.ibmcloud.kickster.web.ArtifactResource;

public interface ArtifactService {

	public byte[] getArtifact(String artifactId) throws Exception;
	public String createArtifact(ArtifactResource artifact) throws Exception;
	
}
