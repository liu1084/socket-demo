package com.jim.socket.netty.server;

import com.jim.socket.config.JSONConfig;
import com.jim.socket.config.ServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.netty.server
 * @author: Administrator
 * @date: 2020-06-12 17:46
 * @description：TODO
 */
public class HelloServer {
	private NioEventLoopGroup boss = new NioEventLoopGroup();
	private NioEventLoopGroup worker = new NioEventLoopGroup();
	private ServerBootstrap serverBootstrap = new ServerBootstrap();
	public void server() {
		try {
			serverBootstrap.group(boss, worker)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new HelloHandler());
						}
					});
			JSONConfig<ServerConfig> jsonConfig =  new JSONConfig<>();
			ServerConfig config = jsonConfig.loadConfig("config/config.json", ServerConfig.class);
			ChannelFuture channel = serverBootstrap
					.bind(config.getBindAddress(), config.getPort())
					.sync();
			channel.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				boss.shutdownGracefully().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				worker.shutdownGracefully().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
