package com.jim.socket.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.netty.buffer
 * @author: Administrator
 * @date: 2020-06-15 16:16
 * @description：TODO
 */

@Slf4j
public class ByteBufferTest {

	@Test
	public void testUnpooledByteBuffer() {
		// 创建一个非池化的buffer
		ByteBuf byteBuf = Unpooled.buffer();
		byte[] data = {33, 34, 'y'};
		// 将数组中的内容写入buffer中
		byteBuf.writeBytes(data);
		Assert.assertFalse(byteBuf.isDirect());
		// byteBuffer的初始化容量是256, 最大是2^32
		Assert.assertEquals(256, byteBuf.capacity());
		Assert.assertEquals(Integer.MAX_VALUE, byteBuf.maxCapacity());

		//断言byteBuffer的可读长度和data相等
		Assert.assertEquals(data.length, byteBuf.readableBytes());

		//断言byteBuffer的第一个字符和data中的第一个字符相等
		Assert.assertEquals(data[0], byteBuf.getByte(0));
		//断言byteBuffer中的第三个字符和data的第三个字符相等
		Assert.assertEquals(0x79, byteBuf.getByte(2));

		//读取byteBuffer中的第一个字符
		byte b1 = byteBuf.readByte();
		//读取byteBuffer中的第二个字符
		byte b2 = byteBuf.readByte();
		Assert.assertEquals(33, b1);
		Assert.assertEquals(34, b2);
		byte b3 = byteBuf.readByte();
		Assert.assertEquals(0x79, b3);

		// 废弃已读的byteBuffer, 读写index回到0位, 之前读的数据再次读的时候将会废弃, 但是数组长度不变
		byteBuf.discardReadBytes();
		Assert.assertEquals(0, byteBuf.readerIndex());
		Assert.assertEquals(0, byteBuf.writerIndex());

		Assert.assertEquals(0, byteBuf.readableBytes());
		Assert.assertEquals(256, byteBuf.writableBytes());

		byte [] r1 = new byte[3];
		r1[0] = 'w';
		//将数组写入buffer中
		byteBuf.writeBytes(r1);

		String value = byteBuf.readCharSequence(1, StandardCharsets.UTF_8).toString();
		Assert.assertEquals("w", value);
	}
}
