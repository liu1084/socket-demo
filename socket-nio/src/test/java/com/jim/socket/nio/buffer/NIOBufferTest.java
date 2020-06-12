package com.jim.socket.nio.buffer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import oshi.SystemInfo;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.ByteBuffer.wrap;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.nio.buffer
 * @author: Administrator
 * @date: 2020-06-12 10:42
 * @description：TODO
 */

@Slf4j
public class NIOBufferTest {
	int bufferSize = 100 * 1024 * 1024;

	@Test
	public void testByteBuffer() {
		log.info("current heap size = {}", Runtime.getRuntime().freeMemory());
		ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
		log.info("current heap size = {}", Runtime.getRuntime().freeMemory());
	}

	@Test
	public void testAllocateDirectMemory() {
		SystemInfo systemInfo = new SystemInfo();
		log.info("current system memory size = {}", systemInfo.getHardware().getMemory().getTotal());
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
		byteBuffer.put("hello".getBytes());
		log.info("limit = {}", byteBuffer.limit());
		log.info("position = {}", byteBuffer.position());
		byteBuffer.flip(); // 执行flip后, limit变为实际占用的空间大小, position变为0
		log.info("limit = {}", byteBuffer.limit());
		log.info("position = {}", byteBuffer.position());

		log.info("current system memory size = {}", systemInfo.getHardware().getMemory().getTotal());
	}

	@Test
	public void testBufferWrap() throws IOException {
		Path target = Paths.get("test.txt");
		ByteBuffer byteBuffer = wrap("hello".getBytes());
		log.info("limit = {}", byteBuffer.limit());
		log.info("position = {}", byteBuffer.position());
		File file = target.toFile();
		Assert.assertTrue(file.exists());
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		FileChannel fileChannel = fileOutputStream.getChannel();
		Assert.assertNotNull(fileChannel);
		Assert.assertTrue(fileChannel.isOpen());

//		Set<OpenOption> fileOpenOption = new HashSet<>();
//		fileOpenOption.add(StandardOpenOption.CREATE);
//		fileOpenOption.add(StandardOpenOption.WRITE);
		int result = fileChannel.write(byteBuffer);
		fileChannel.close();
		Assert.assertEquals("hello".length(), result);
		Assert.assertFalse(fileChannel.isOpen());
		// read from target
		byteBuffer.flip();
		FileInputStream inputStream = new FileInputStream(file);
		fileChannel = inputStream.getChannel();
		Assert.assertTrue(fileChannel.isOpen());
		while (fileChannel.read(byteBuffer) > 0) {
			fileChannel.read(byteBuffer);
		}

		Assert.assertEquals(5, byteBuffer.limit());
		byteBuffer.flip();
		// get()方法取的是第一个字符
		byte bytes = byteBuffer.get();
		log.info("bytes = {}", (char) bytes);
		Assert.assertEquals('l', (char) byteBuffer.get(2));
	}


	@Test
	public void testBufferWrap2() {
		ByteBuffer byteBuffer = wrap("hello".getBytes(), 2, 3);
		Assert.assertEquals(2, byteBuffer.position());
		Assert.assertEquals(5, byteBuffer.limit());
		byteBuffer.flip();
		Assert.assertEquals('h', byteBuffer.get());
		Assert.assertEquals('e', byteBuffer.get(1));
	}

	@Test
	public void testMark() throws IOException {
		ByteBuffer byteBuffer = wrap("hello".getBytes());
		Assert.assertEquals(5, byteBuffer.limit());
		Assert.assertEquals(0, byteBuffer.position());
//		byte second = byteBuffer.get(2);
//		byteBuffer.put(2, (byte) 'c'); //替换索引为2的'l' => 'c'
//		Assert.assertEquals(byteBuffer.get(2),99);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		byteBuffer.flip();
		for (int i = 0; i < byteBuffer.capacity(); i++) {
			outputStream.write(byteBuffer.get(i));
		}
	}
}
