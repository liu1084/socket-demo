package com.jim.socket.bio;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.bio
 * @author: Administrator
 * @date: 2020-06-06 18:50
 * @description：TODO
 */
public class BIOSocketServerTest {
	private static final Logger LOG = LoggerFactory.getLogger(BIOSocketServerTest.class);
	private static final int TIMES = 10;
	private Socket client;
	@Test
	public void testSocketConnection() throws IOException {
		client = new Socket("localhost", 8089);
		boolean isConnected = client.isConnected();
		Assert.assertTrue(isConnected);
	}

	@Test
	public void testSendMessageToServer() {
		DataOutputStream outputStream = null;
		try {
			client = new Socket("localhost", 8089);
			byte [] messageFrame = new byte[100];
			messageFrame[0] = 0x01; //type
			for (int i = 0; i < TIMES; i++) {
				String data = "ping";
				messageFrame[1] = (byte) data.length(); //length
				System.arraycopy(data.getBytes(),0,messageFrame,2, data.length());
				outputStream = new DataOutputStream(client.getOutputStream());
				outputStream.write(messageFrame);
				outputStream.flush();

				LOG.debug("Response data = {}.", messageFrame);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
