package com.jim.socket.netty.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @project: socket-demo
 * @packageName: com.jim.socket.netty.bean
 * @author: Administrator
 * @date: 2020-06-16 21:23
 * @description：TODO
 */
@Data
public class Message implements Serializable {
	private static final long serialVersionUID = 6168799575882942694L;
	private Long id;
	private String body;
	private EnumMessageType messageType;
}
