package com.jim.socket.config;

import java.io.Reader;

/**
 * @project: socket-demo
 * @packageName: PACKAGE_NAME
 * @author: Administrator
 * @date: 2020-06-08 12:55
 * @description：TODO
 */
public interface IResourceLoader {

	Reader loadDefaultResource();

	Reader loadResource(String relativePath);

	String getName();

	class ResourceIsDirectoryException extends RuntimeException {

		private static final long serialVersionUID = -6969292229582764176L;

		public ResourceIsDirectoryException(String message) {
			super(message);
		}
	}

}
