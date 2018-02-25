package com.rockstar.artifact.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;

import com.rockstar.artifact.model.GeneratedFile;
import com.rockstar.artifact.model.GeneratedProject;
import com.rockstar.artifact.util.ZipUtils;

public class DownloadUtils {
	
	public static void createZipfile(GeneratedProject generatedProject, String filename) throws Exception {
		String outputPath = FileUtils.getUserDirectoryPath() + File.separator + "Downloads" + File.separator + filename;
		FileOutputStream outputFileStream = null;
		File outputDirectory = null;
		File outputFile = null;
		
		for (GeneratedFile current : generatedProject.getFiles()) {
			outputDirectory = new File(outputPath + File.separator + current.getPath());
			outputDirectory.mkdirs();
			outputFile = new File(outputDirectory, current.getFilename());
			if (!outputFile.exists()) {
				outputFile.createNewFile();
			}
			outputFileStream = new FileOutputStream(outputFile);
			outputFileStream.write(current.getContent().getBytes());
			outputFileStream.close();
		}
		
		ZipUtils.zip(outputPath, outputPath + ".zip");
    		FileUtils.deleteDirectory(new File(outputPath));
	}

}
