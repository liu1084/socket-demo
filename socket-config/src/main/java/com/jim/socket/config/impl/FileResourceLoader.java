package com.jim.socket.config.impl;

import com.jim.socket.config.IResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.config.impl
 * @author: Administrator
 * @date: 2020-06-08 12:57
 * @description：TODO
 */
public class FileResourceLoader implements IResourceLoader {
	private static final Logger LOG = LoggerFactory.getLogger(FileResourceLoader.class);

	private final File defaultFile;
	private final String parentPath;

	public FileResourceLoader(File defaultFile, String parentPath) {
		this.defaultFile = defaultFile;
		this.parentPath = parentPath;
	}

	public FileResourceLoader(File defaultFile) {
		this(defaultFile, System.getProperty("config.json", null));
	}

	public FileResourceLoader() {
		this(null);
	}


	@Override
	public Reader loadDefaultResource() {
		if (defaultFile == null) {
			throw new IllegalArgumentException("Default config file is not set.");
		}
		return loadResource(defaultFile);
	}

	@Override
	public Reader loadResource(String relativePath) {
		return loadResource(new File(parentPath, relativePath));
	}

	@Override
	public String getName() {
		return "file";
	}

	private Reader loadResource(File file) {
		LOG.info("Loading file. Path = {}", file.getAbsolutePath());
		if (file.isDirectory()) {
			LOG.error("The given file is a directory. Path = {}.", file.getAbsolutePath());
			throw new ResourceIsDirectoryException("File " + file.getAbsolutePath() + "is a directory.");
		}

		try {
			return Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
