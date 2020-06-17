package com.jim.socket.netty.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jim.socket.netty.bean.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.netty.server
 * @author: Administrator
 * @date: 2020-06-16 21:22
 * @description：TODO
 */
public class MessageEncoderDecoder {
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static final Logger log = LoggerFactory.getLogger(MessageEncoderDecoder.class);

	public static class MessageEncoder extends MessageToByteEncoder<Message> {
		@Override
		protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
			log.info("{}", ctx.channel().metadata());
			String msgJSON = objectMapper.writeValueAsString(msg);
			out.writeBytes(msgJSON.getBytes());
		}
	}

	public static class MessageDecoder extends ByteToMessageDecoder {
		@Override
		protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
			log.info("{}", ctx.channel().metadata());
			if (in.readableBytes() < 4) return;
			String msgJSON = in.toString(StandardCharsets.UTF_8);
			Message message = objectMapper.readValue(msgJSON, Message.class);
			in.readerIndex(in.readerIndex() + in.readableBytes());
			out.add(message);
		}
	}
}
