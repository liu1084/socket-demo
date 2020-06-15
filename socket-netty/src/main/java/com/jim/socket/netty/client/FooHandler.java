package com.jim.socket.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

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
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelActive();
		ctx.writeAndFlush(Unpooled.copiedBuffer("foo".getBytes()));
		log.info("Send Info successful.");
	}
}
