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
 * @date: 2020-06-11 13:16
 * @description：TODO
 */

@Slf4j
public class TCPWriteThread implements Runnable {
	private Selector selector;

	public TCPWriteThread(Selector selector) {
		this.selector = selector;
	}


	@Override
	public void run() {
		try {
			while (selector.select() > 0) {
				for (SelectionKey key : selector.keys()) {
					if (key.isWritable()) {
						SocketChannel socketChannel = (SocketChannel) key.channel();
						ByteBuffer byteBuffer = ByteBuffer.wrap("ping".getBytes());
						socketChannel.write(byteBuffer);
						log.info("Write info = {} on channel = {}", byteBuffer.get(), socketChannel);
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
