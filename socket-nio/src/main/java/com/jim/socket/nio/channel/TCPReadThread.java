package com.jim.socket.nio.channel;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.nio.channel
 * @author: Administrator
 * @date: 2020-06-11 11:23
 * @description：TODO
 */

@Slf4j
public class TCPReadThread implements Runnable {
	private Selector selector;

	public TCPReadThread(Selector selector) {
		this.selector = selector;
	}


	@Override
	public void run() {
		try {
			while (selector.select() > 0) {
				for (SelectionKey key : selector.keys()) {
					if (key.isReadable()) {
						SocketChannel socketChannel = (SocketChannel) key.channel();
						ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
						while (socketChannel.read(byteBuffer) > 0) {
							byteBuffer.flip();
							socketChannel.read(byteBuffer);
						}
						log.info("Read info = {}",byteBuffer.get());
						socketChannel.close();
					}
					selector.keys().remove(key);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
