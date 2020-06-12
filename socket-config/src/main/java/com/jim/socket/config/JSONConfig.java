package com.jim.socket.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.config
 * @author: Administrator
 * @date: 2020-06-10 18:54
 * @description：TODO
 */
public class JSONConfig<T> {
	private ObjectMapper objectMapper = new ObjectMapper();
	private T config = null;

	public T loadConfig(String path, Class<T> clazz) {
 		URL url = Thread.currentThread().getContextClassLoader().getResource(path);
		try {
			config = objectMapper.readValue(url, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return config;
	}
}
