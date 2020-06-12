package com.jim.socket.nio.channel.socket;

import com.jim.socket.config.JSONConfig;
import com.jim.socket.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.nio.channel.socket
 * @author: Administrator
 * @date: 2020-06-11 9:11
 * @description：TODO
 */

@Slf4j
public class TCPServer {
	private static final int BUFFER_SIZE = 1024;
	private static final int TIMEOUT = 30000; //30秒

	public static void main(String[] args) {

		try {
			//创建selector
			Selector selector = Selector.open();
			//打卡监听信道
			ServerSocketChannel listener = ServerSocketChannel.open();
			//绑定端口
			String configPath = "config/config.json";
			JSONConfig<ServerConfig> serverConfigJSONConfig = new JSONConfig<>();
			ServerConfig config = serverConfigJSONConfig.loadConfig(configPath, ServerConfig.class);
			listener.socket().bind(new InetSocketAddress(config.getBindAddress(), config.getPort()));
			//设置为非阻塞
			listener.configureBlocking(false);
			// 绑定选择器到监听器上
			listener.register(selector, SelectionKey.OP_ACCEPT);
			// handler
			TCPHandler handler = new TCPHandler(BUFFER_SIZE);
			while (true) {
				if (selector.select(TIMEOUT) == 0) {
					log.info("等待超时: {}", TIMEOUT);
					continue;
				}

				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					if (key.isConnectable()) {
						log.info("Socket channel is connectable now.");
					}

					if (key.isAcceptable()) {
						handler.handlerAccept(key);
					}

					if (key.isReadable()) {
						handler.handleRead(key);
					}

					if (key.isWritable()) {
						handler.handleWrite(key);
					}

					iterator.remove();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
