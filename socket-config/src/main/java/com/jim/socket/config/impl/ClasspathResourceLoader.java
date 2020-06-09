package com.jim.socket.config.impl;

import com.jim.socket.config.IConfig;
import com.jim.socket.config.IResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.config.impl
 * @author: Administrator
 * @date: 2020-06-08 12:56
 * @description：TODO
 */
public class ClasspathResourceLoader implements IResourceLoader {
	private static final Logger LOG = LoggerFactory.getLogger(ClasspathResourceLoader.class);

	private final String defaultResource;
	private final ClassLoader classLoader;

	public ClasspathResourceLoader(String defaultResource, ClassLoader classLoader) {
		this.defaultResource = defaultResource;
		this.classLoader = classLoader;
	}

	public ClasspathResourceLoader(String defaultResource) {
		this(defaultResource, Thread.currentThread().getContextClassLoader());
	}

	public ClasspathResourceLoader() {
		this(IConfig.DEFAULT_CONFIG);
	}

	@Override
	public Reader loadDefaultResource() {
		return loadResource(defaultResource);
	}

	@Override
	public Reader loadResource(String relativePath) {
		LOG.info("Loading resource. Relative Path = {}.", relativePath);
		InputStream in = this.classLoader.getResourceAsStream(relativePath);
		return in == null ? null : new InputStreamReader(in);
	}

	@Override
	public String getName() {
		return "classpath resource";
	}
}
