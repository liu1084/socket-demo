package com.jim.socket.bio.impl;

import com.jim.socket.bio.BIOSocketServer;
import com.jim.socket.bio.IBIOSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.bio.impl
 * @author: Administrator
 * @date: 2020-06-09 14:56
 * @description：TODO
 */
public class BIOSocketHandler implements IBIOSocketHandler, Runnable {
	//TLV
	/**
	 * data frame size: 100
	 * 1st Byte: data type,
	 * 2nd Byte: date length
	 * 3~100: data content
	 */
	private byte[] messageFrame = new byte[100];
	private Socket clientSocket;
	private static final Logger LOG = LoggerFactory.getLogger(BIOSocketHandler.class);
	// 是否已经读到末端数据
	private boolean isEnd;
	private String data = "";

	public BIOSocketHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void handleData() {
		read();
		write();
	}

	@Override
	public void run() {
		handleData();
	}

	private void write() {
		// 写入
		try {
			messageFrame = new byte[100];
			messageFrame[0] = 0x01;
			String message = "Pong";
			System.arraycopy(message.getBytes(), 0, messageFrame, 2, message.length());
			int length = messageFrame.length;
			messageFrame[1] = (byte) length;
			OutputStream outputStream = null;
			outputStream = clientSocket.getOutputStream();
			outputStream.write(messageFrame);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void read() {
		// 读取
		try {
			DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
			int byteRead = 0;
			messageFrame[0] = dataInputStream.readByte();
			messageFrame[1] = dataInputStream.readByte();
			byteRead = messageFrame[1];
			while (!isEnd) {
				byteRead = dataInputStream.read(messageFrame);
				data += new String(messageFrame, 0, byteRead);
				if (data.length() == byteRead) {
					isEnd = true;
				}
			}
			LOG.info("Message: {}", data.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
