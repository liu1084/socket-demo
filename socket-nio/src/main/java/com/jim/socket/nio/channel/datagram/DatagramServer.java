package com.jim.socket.nio.channel.datagram;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jim.socket.config.JSONConfig;
import com.jim.socket.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.nio.channel.datagram
 * @author: Administrator
 * @date: 2020-06-10 11:17
 * @description：UDP 协议
 */

@Slf4j
public class DatagramServer implements IDatagramChannelServer, Runnable {
	private ExecutorService executorServicePool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

	@Override
	public void startServer(ServerConfig config) {
		try {
			int port = config.getPort();
			String ipAddress = config.getBindAddress();
			DatagramChannel datagramChannel = DatagramChannel.open();
			SocketAddress socketAddress = new InetSocketAddress(ipAddress, port);
			datagramChannel.bind(socketAddress);
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			SocketAddress remote = datagramChannel.receive(byteBuffer);
			while (byteBuffer.hasRemaining()) {
				byteBuffer.flip();
				int limits = byteBuffer.limit();
				byte [] data = new byte[limits];
				byteBuffer.get(data, 0,limits);
				String message = new String(data);
				log.info("client = {}, message = {}",remote,message);

				ByteBuffer response = ByteBuffer.wrap("pong".getBytes());
				datagramChannel.send(response,remote);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		JSONConfig<ServerConfig> jsonConfig = new JSONConfig<>();
		ServerConfig serverConfig = jsonConfig.loadConfig("config/config.json", ServerConfig.class);
		DatagramServer datagramServer = new DatagramServer();
		datagramServer.startServer(serverConfig);
	}

	public static void main(String[] args) {
		ExecutorService executorServicePool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
		executorServicePool.submit(new DatagramServer());
		executorServicePool.shutdown();
	}
}