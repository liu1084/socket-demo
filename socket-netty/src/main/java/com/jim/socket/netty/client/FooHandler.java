package com.jim.socket.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.netty.client
 * @author: Administrator
 * @date: 2020-06-12 18:11
 * @description：TODO
 */
@Slf4j
public class FooHandler extends SimpleChannelInboundHandler<ByteBuf> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		byte [] req = new byte[msg.readableBytes()];
		msg.readBytes(req);
		String body = new String(req, StandardCharsets.UTF_8);
		log.info("Received info = {}",body);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelActive();
		ctx.writeAndFlush(Unpooled.copiedBuffer("foo".getBytes()));
		log.info("Send Info successful.");
	}
}
