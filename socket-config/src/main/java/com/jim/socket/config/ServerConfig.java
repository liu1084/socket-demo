package com.jim.socket.config;

import lombok.Data;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.utils
 * @author: Administrator
 * @date: 2020-06-06 17:28
 * @description：TODO
 */
@Data
public class ServerConfig {
	private String bindAddress;
	private int port;
	private int maxConnection;
}
