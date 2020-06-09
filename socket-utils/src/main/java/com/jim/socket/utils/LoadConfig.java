package com.jim.socket.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.nio.file.Paths;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.utils
 * @author: Administrator
 * @date: 2020-06-06 17:28
 * @description：TODO
 */
public class LoadConfig {
	private static ObjectMapper objectMapper = new ObjectMapper();
	public static ServerConfig getServerConfig(String config) throws Exception {
		URL configURL = ClassLoader.getSystemResource(config);
		if (configURL == null) {
			throw new Exception("配置文件: " + configURL.getPath() + "不存在.");
		}
		String path = configURL.getPath();
		ServerConfig serverConfig = objectMapper.readValue(Paths.get("I:\\green26\\socket-demo\\socket-bio\\src\\main\\resources\\config.json").toFile(), ServerConfig.class);
		return serverConfig;
	}
}
