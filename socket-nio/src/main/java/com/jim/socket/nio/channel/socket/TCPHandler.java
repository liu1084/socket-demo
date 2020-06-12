package com.jim.socket.nio.channel.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.nio.channel.socket
 * @author: Administrator
 * @date: 2020-06-11 9:18
 * @description：TODO
 */
public class TCPHandler implements TCPProtocolHandler {
	private int bufferSize;

	public TCPHandler(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	@Override
	public void handlerAccept(SelectionKey selectionKey) throws IOException {
		SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
		socketChannel.configureBlocking(false);
		socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ, bufferSize);
	}

	@Override
	public void handleRead(SelectionKey selectionKey) throws IOException {
		try {
			SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
			ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
			byteBuffer.clear();
			int bytesRead = socketChannel.read(byteBuffer);

			if (bytesRead == -1) {
				socketChannel.close();
			}

			while (socketChannel.read(byteBuffer) > 0) {
				byteBuffer.flip();
				socketChannel.read(byteBuffer);
			}
		}catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void handleWrite(SelectionKey selectionKey) throws IOException {
		SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
		ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
		byteBuffer.clear();
		byteBuffer = ByteBuffer.wrap("pong".getBytes());
		socketChannel.write(byteBuffer);
	}
}
