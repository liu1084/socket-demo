package com.jim.socket.netty;

import com.jim.socket.netty.client.ISocketClient;
import com.jim.socket.netty.client.NettyClient;
import com.jim.socket.netty.client.SocketClient;
import com.jim.socket.netty.server.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.netty
 * @author: Administrator
 * @date: 2020-06-12 18:19
 * @description：TODO
 */
@Slf4j
public class HelloServerTest {

	@Test
	public void testStartHelloServer() throws InterruptedException {
		NettyServer server = new NettyServer();
		server.server();
	}

	@Test
	public void testClient() {
		ISocketClient client = new SocketClient();
		try {
			client.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testClientWithNetty() {
		ISocketClient client = new NettyClient();
		try {
			client.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
