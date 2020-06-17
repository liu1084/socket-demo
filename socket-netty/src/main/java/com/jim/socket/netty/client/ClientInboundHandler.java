package com.jim.socket.netty.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jim.socket.netty.bean.EnumMessageType;
import com.jim.socket.netty.bean.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
public class ClientInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		log.info("{}",msg.readCharSequence(msg.readableBytes(), StandardCharsets.UTF_8));
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelActive();
		Message message = new Message();
		message.setId(1L);
		message.setMessageType(EnumMessageType.TXT);
		message.setBody("foo");
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(message);
		ctx.writeAndFlush(Unpooled.wrappedBuffer(json.getBytes()));
		log.info("Send message successful.");
	}
}
