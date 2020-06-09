package com.jim.socket.config;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.config
 * @author: Administrator
 * @date: 2020-06-08 13:02
 * @description：TODO
 */
public abstract class IConfig {

	public static final String DEFAULT_CONFIG = "config.json";

	public abstract void setProperty(String name, String value);

	/**
	 * Same semantic of Properties
	 *
	 * @param name property name.
	 * @return property value.
	 * */
	public abstract String getProperty(String name);

	/**
	 * Same semantic of Properties
	 *
	 * @param name property name.
	 * @param defaultValue default value to return in case the property doesn't exists.
	 * @return property value.
	 * */
	public abstract String getProperty(String name, String defaultValue);

	void assignDefaults() {
		setProperty(SocketConstants.BIND_IP_ADDRESS, SocketConstants.BIND_IP_ADDRESS);
		setProperty(SocketConstants.SERVER_PORT_NAME, String.valueOf(SocketConstants.SERVER_PORT));
		setProperty(SocketConstants.MAX_CONNECTIONS_NAME,String.valueOf(SocketConstants.MAX_CONNECTIONS));
	}

	public abstract IResourceLoader getResourceLoader();

	public int intProp(String propertyName, int defaultValue) {
		String propertyValue = getProperty(propertyName);
		if (propertyValue == null) {
			return defaultValue;
		}
		return Integer.parseInt(propertyValue);
	}

	public boolean boolProp(String propertyName, boolean defaultValue) {
		String propertyValue = getProperty(propertyName);
		if (propertyValue == null) {
			return defaultValue;
		}
		return Boolean.parseBoolean(propertyValue);
	}
}
