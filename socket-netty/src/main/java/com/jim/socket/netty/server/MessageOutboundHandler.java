package com.jim.socket.netty.server;

import com.jim.socket.netty.bean.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.netty.server
 * @author: Administrator
 * @date: 2020-06-12 17:54
 * @description：TODO
 */


@Slf4j
public class MessageOutboundHandler extends ChannelOutboundHandlerAdapter {
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		ctx.write(msg, promise);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.fireExceptionCaught(cause);
		ctx.close();
	}
}
