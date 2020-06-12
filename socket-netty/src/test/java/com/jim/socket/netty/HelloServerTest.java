package com.jim.socket.netty;

import com.jim.socket.netty.client.HelloClient;
import com.jim.socket.netty.server.HelloServer;
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
		HelloServer server = new HelloServer();
		server.server();
	}

	@Test
	public void testClient() {
		HelloClient client = new HelloClient();
		client.run();
	}
}
