package com.jim.socket.bio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jim.socket.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.bio
 * @author: Administrator
 * @date: 2020-06-06 17:18
 * @description：TODO
 */
public class BIOSocketServer {
	private static final Logger LOG = LoggerFactory.getLogger(BIOSocketServer.class);
	//TLV
	/**
	 * data frame size: 100
	 * 1st Byte: data type,
	 * 2nd Byte: date length
	 * 3~100: data content
	 */
	private byte[] messageFrame = new byte[100];
	// 是否已经读到末端数据
	private boolean isEnd;
	private String data = "";
	private ObjectMapper mapper = new ObjectMapper();

	public void startServer(ServerConfig config) throws Exception {
		int serverPort = config.getPort();
		int maxConnections = config.getMaxConnection();
		ServerSocket serverSocket = new ServerSocket(serverPort, maxConnections);
		try {
			for (; ; ) {
				Socket clientSocket = serverSocket.accept();
				LOG.info("Accepted connection from : {}", clientSocket);
				OutputStream outputStream = clientSocket.getOutputStream();
				new Thread(() -> {
					try {
						// 读取
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
						// 写入

						messageFrame = new byte[100];
						messageFrame[0] = 0x01;
						String message = "Pong";
						System.arraycopy(message.getBytes(), 0, messageFrame, 2, message.length());
						int length = messageFrame.length;
						messageFrame[1] = (byte) length;
						outputStream.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}).start();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private byte[] intToByteArray(final int i) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeInt(i);
		dos.flush();
		return bos.toByteArray();
	}

	private void startServer() throws Exception {
		URL defaultConfig = Thread.currentThread().getContextClassLoader().getResource("config/config.json");
		ServerConfig serverConfig = mapper.readValue(defaultConfig, ServerConfig.class);
		startServer(serverConfig);
	}

	public static void main(String[] args) {
		BIOSocketServer bioSocketServer = new BIOSocketServer();
		try {
			bioSocketServer.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
