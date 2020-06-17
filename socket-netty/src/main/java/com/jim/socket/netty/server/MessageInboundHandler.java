package com.jim.socket.netty.server;

import com.jim.socket.netty.bean.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
public class MessageInboundHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message) {
			try {
				Message message = (Message) msg;
				// TODO
				log.info("收到客户端发来的message消息 = {}", message);

				// 业务逻辑
				String body = message.getBody();

				// 回写给客户端
				ByteBuf response = Unpooled.wrappedBuffer(body.getBytes());
				ctx.channel().writeAndFlush(response);
			}catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.fireExceptionCaught(cause);
		ctx.close();
	}
}
