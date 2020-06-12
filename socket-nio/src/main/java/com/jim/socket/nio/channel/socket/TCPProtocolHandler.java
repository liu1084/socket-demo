package com.jim.socket.nio.channel.socket;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.nio.channel.socket
 * @author: Administrator
 * @date: 2020-06-11 9:22
 * @description：TODO
 */
public interface TCPProtocolHandler {
	// 接收SocketChannel的Accept处理
	void handlerAccept(SelectionKey selectionKey) throws IOException;

	// 处理SocketChannel的Read
	void handleRead(SelectionKey selectionKey) throws IOException;

	// 处理SocketChannel的写
	void handleWrite(SelectionKey selectionKey) throws IOException;
}
