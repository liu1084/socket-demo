package com.jim.socket.netty.client;

import com.jim.socket.config.JSONConfig;
import com.jim.socket.config.ServerConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.netty.client
 * @author: Administrator
 * @date: 2020-06-12 18:05
 * @description：TODO
 */
public class HelloClient {
	private NioEventLoopGroup worker = new NioEventLoopGroup();

	public void run() {
		try {
			Bootstrap bootstrap = new Bootstrap();
			JSONConfig<ServerConfig> jsonConfig =  new JSONConfig<>();
			ServerConfig config = jsonConfig.loadConfig("config/config.json", ServerConfig.class);
			bootstrap.group(worker)
					.channel(NioSocketChannel.class)
					.remoteAddress(config.getBindAddress(), config.getPort())
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new FooHandler());
						}

					});
			bootstrap.connect().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				worker.shutdownGracefully().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
