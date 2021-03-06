package com.jim.socket.nio.channel;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.nio.channel
 * @author: Administrator
 * @date: 2020-06-09 16:39
 * @description：TODO
 */

@Slf4j
public class NIOFileChannel {

	@Test
	public void readFile() {
		URL url = Thread.currentThread().getContextClassLoader().getResource("config/config.json");
		if (url != null) {
			try (
					RandomAccessFile file = new RandomAccessFile(new File(url.toURI()), "r");
					FileChannel fileChannel = file.getChannel();
			) {

				ByteBuffer byteBuffer = ByteBuffer.allocate(512);
				//读取文件
				while (fileChannel.read(byteBuffer) > 0) {
					byteBuffer.flip();
					while (byteBuffer.hasRemaining()) {
						log.info("byteBuffer content = {}", (char) byteBuffer.get());
					}
				}
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}

	}

	@Test
	public void readFile2() {
		URL url = Thread.currentThread().getContextClassLoader().getResource("config/config.json");
		if (url != null) {
			try (FileInputStream inputStream = new FileInputStream(Paths.get(url.toURI()).toFile());
				 FileChannel fileChannel = inputStream.getChannel()) {
				Optional<FileChannel> optionalFileChannel = Optional.ofNullable(fileChannel);
				optionalFileChannel.ifPresent((f) -> {
					ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
					try {
						while (fileChannel.read(byteBuffer) > 0) {
							byteBuffer.flip();
							while (byteBuffer.hasRemaining()) {
								log.info("content = {}", (char) byteBuffer.get());
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			} catch (URISyntaxException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void writeFile() {
		URL url = Thread.currentThread().getContextClassLoader().getResource("config/config.json");
		Set<StandardOpenOption> openOptions = new HashSet<>();
		openOptions.add(StandardOpenOption.WRITE);
		openOptions.add(StandardOpenOption.APPEND);
		FileChannel fileChannel = null;
		try {
			Path path = Paths.get(url.toURI());
			fileChannel = FileChannel.open(path, openOptions);
			ByteBuffer byteBuffer = ByteBuffer.wrap("Hello".getBytes());
			fileChannel.write(byteBuffer);
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		} finally {
			if (fileChannel != null) {
				try {
					fileChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
