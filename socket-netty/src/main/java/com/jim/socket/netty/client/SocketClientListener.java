package com.jim.socket.netty.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.netty.client
 * @author: Administrator
 * @date: 2020-06-17 16:54
 * @description：TODO
 */
public class SocketClientListener implements Runnable {
	protected Socket client;
	public SocketClientListener(Socket socket) {
		this.client = socket;
	}

	@Override
	public void run() {
		if (client == null) return;
		try {
			InputStream in = client.getInputStream();
			byte [] buffer = new byte[128];
			int read = 0;
			while (read != -1) {
				read = in.read(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (client != null){
					client.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
