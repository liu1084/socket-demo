package com.jim.socket.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.netty.server
 * @author: Administrator
 * @date: 2020-06-12 17:54
 * @description：TODO
 */


@Slf4j
public class HelloHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof ByteBuf) {
			ByteBuf byteBuf = (ByteBuf) msg;
			byte [] result = new byte[((ByteBuf) msg).readableBytes()];
			result[0] = 0x79;
			byteBuf.writeBytes(result);
			log.info("Result = {}", Arrays.toString(result));
			log.info("Received message = {}", ((ByteBuf) msg).toString(StandardCharsets.UTF_8));
			ctx.writeAndFlush("bar".getBytes(StandardCharsets.UTF_8));
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.fireExceptionCaught(cause);
		ctx.close();
	}
}
