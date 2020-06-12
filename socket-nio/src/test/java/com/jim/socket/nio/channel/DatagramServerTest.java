package com.jim.socket.nio.channel;

import com.jim.socket.config.JSONConfig;
import com.jim.socket.config.ServerConfig;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Optional;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.nio.channel
 * @author: Administrator
 * @date: 2020-06-10 11:30
 * @description：TODO
 */
public class DatagramServerTest {

	@Test
	public void testDatagram() {
		try (
				DatagramChannel client = DatagramChannel.open();
		) {
			JSONConfig<ServerConfig> jsonConfig = new JSONConfig<>();
			Optional<ServerConfig> serverConfig = Optional.of(jsonConfig.loadConfig("config/config.json", ServerConfig.class));
			serverConfig.ifPresent((config) -> {
				try {
					client.bind(null);
					ByteBuffer message = ByteBuffer.wrap("ping".getBytes());
					SocketAddress serverAddress = new InetSocketAddress(config.getBindAddress(), config.getPort());
					client.send(message, serverAddress);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
