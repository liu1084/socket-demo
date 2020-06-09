package com.jim.socket.bio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jim.socket.bio.impl.BIOSocketHandler;
import com.jim.socket.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.bio
 * @author: Administrator
 * @date: 2020-06-06 17:18
 * @description：TODO
 */
public class BIOSocketServer {
	private static final Logger LOG = LoggerFactory.getLogger(BIOSocketServer.class);
	private ObjectMapper mapper = new ObjectMapper();

	private void startServer(ServerConfig config) throws Exception {
		int serverPort = config.getPort();
		int maxConnections = config.getMaxConnection();
		ServerSocket serverSocket = new ServerSocket(serverPort, maxConnections);
		try {
			for (; ; ) {
				Socket clientSocket = serverSocket.accept();
				LOG.info("Accepted connection from : {}", clientSocket);
				new Thread(() -> {
					new BIOSocketHandler(clientSocket);
				}).start();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private byte[] intToByteArray(final int i) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeInt(i);
		dos.flush();
		return bos.toByteArray();
	}

	private void startServer() throws Exception {
		URL defaultConfig = Thread.currentThread().getContextClassLoader().getResource("config/config.json");
		ServerConfig serverConfig = mapper.readValue(defaultConfig, ServerConfig.class);
		startServer(serverConfig);
	}

	public static void main(String[] args) {
		BIOSocketServer bioSocketServer = new BIOSocketServer();
		try {
			bioSocketServer.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
