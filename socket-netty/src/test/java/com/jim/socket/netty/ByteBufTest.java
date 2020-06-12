package com.jim.socket.netty;

import io.netty.buffer.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.netty
 * @author: Administrator
 * @date: 2020-06-12 15:54
 * @description：TODO
 */

@Slf4j
public class ByteBufTest {
	@Test
	public void testByteBuf() {
		UnpooledByteBufAllocator allocator = UnpooledByteBufAllocator.DEFAULT;
		ByteBuf byteBuf = new UnpooledDirectByteBuf(allocator, 5, 10);
		byteBuf.writeBytes("hello".getBytes());
		log.info("{}", (char) byteBuf.getByte(0));
	}
}
