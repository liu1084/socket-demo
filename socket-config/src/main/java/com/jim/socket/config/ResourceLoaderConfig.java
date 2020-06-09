package com.jim.socket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.Reader;
import java.text.ParseException;
import java.util.Properties;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.config
 * @author: Administrator
 * @date: 2020-06-08 15:43
 * @description：TODO
 */
public class ResourceLoaderConfig extends IConfig {
	private static final Logger LOG = LoggerFactory.getLogger(ResourceLoaderConfig.class);

	private final Properties m_properties;
	private final IResourceLoader resourceLoader;

	public ResourceLoaderConfig(IResourceLoader resourceLoader) {
		this(resourceLoader, null);
	}

	public ResourceLoaderConfig(IResourceLoader resourceLoader, String configName) {
		LOG.info("Loading configuration. ResourceLoader = {}, configName = {}.", resourceLoader.getName(), configName);
		this.resourceLoader = resourceLoader;
		LOG.info(
				"Parsing configuration properties. ResourceLoader = {}, configName = {}.",
				resourceLoader.getName(),
				configName);
		ConfigurationParser confParser = new ConfigurationParser();
		m_properties = confParser.getProperties();
		assignDefaults();
		try {
			confParser.parse(new File(configName));
		} catch (ParseException pex) {
			LOG.warn(
					"Unable to parse configuration properties. Using default configuration. "
							+ "ResourceLoader = {}, configName = {}, cause = {}, errorMessage = {}.",
					resourceLoader.getName(),
					configName,
					pex.getCause(),
					pex.getMessage());
		}
	}

	@Override
	public void setProperty(String name, String value) {
		m_properties.setProperty(name, value);
	}

	@Override
	public String getProperty(String name) {
		return m_properties.getProperty(name);
	}

	@Override
	public String getProperty(String name, String defaultValue) {
		return m_properties.getProperty(name, defaultValue);
	}

	@Override
	public IResourceLoader getResourceLoader() {
		return resourceLoader;
	}
}
