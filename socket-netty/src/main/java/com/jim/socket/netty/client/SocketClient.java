package com.jim.socket.netty.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jim.socket.config.JSONConfig;
import com.jim.socket.config.ServerConfig;
import com.jim.socket.netty.bean.EnumMessageType;
import com.jim.socket.netty.bean.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.Arrays;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.netty.client
 * @author: Administrator
 * @date: 2020-06-17 14:42
 * @description：TODO
 */
public class SocketClient implements ISocketClient {
	private static final Logger log = LoggerFactory.getLogger(SocketClient.class);

	@Override
	public void run() throws Exception {
		Socket socket = new Socket();
		try {
			JSONConfig<ServerConfig> jsonConfig = new JSONConfig<>();
			ServerConfig config = jsonConfig.loadConfig("config/config.json", ServerConfig.class);
			SocketAddress socketAddress = new InetSocketAddress(config.getBindAddress(), config.getPort());
			socket.connect(socketAddress);
			socket.setKeepAlive(true);
			socket.setSoTimeout(10000);

			Message message = new Message();
			message.setId(1L);
			message.setMessageType(EnumMessageType.TXT);
			message.setBody("foo");
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(message);
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(json.getBytes());

			byte[] result = new byte[32];
			for (; ; ) {
				InputStream response = socket.getInputStream();
				int read = response.read(result);
				log.info("length = {} bytes, content = {}", read, Arrays.toString(result));
			}
		} catch (SocketTimeoutException e2) {
			socket.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
