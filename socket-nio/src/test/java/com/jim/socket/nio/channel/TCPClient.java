package com.jim.socket.nio.channel;

import com.jim.socket.config.JSONConfig;
import com.jim.socket.config.ServerConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.nio.channel
 * @author: Administrator
 * @date: 2020-06-11 11:15
 * @description：TODO
 */
public class TCPClient {
	// 信道选择器
	private Selector selector;

	// 与服务器通信的信道
	private SocketChannel socketChannel;

	// 服务器的配置
	private ServerConfig serverConfig;


	@Before
	public void init() {
		String configPath = "config/config.json";
		JSONConfig<ServerConfig> serverConfigJSONConfig = new JSONConfig<>();
		serverConfig = serverConfigJSONConfig.loadConfig(configPath, ServerConfig.class);
	}

	@Test
	public void sendMessage() {
		try {
			socketChannel = SocketChannel.open(new InetSocketAddress(serverConfig.getBindAddress(), serverConfig.getPort()));
			socketChannel.configureBlocking(false);
			selector = Selector.open();
			socketChannel.register(selector, SelectionKey.OP_WRITE);
			Thread writeThread = new Thread(() -> {
				new TCPWriteThread(selector);
			});

			socketChannel.register(selector,SelectionKey.OP_READ);
			Thread readThread = new Thread(() -> {
				new TCPReadThread(selector);
			});
			writeThread.start();
			writeThread.join();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
