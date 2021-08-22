package com.geek.java.week3.gateway.outbound;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HttpClientOutboundHandler extends SimpleChannelInboundHandler<ByteBuf> {

	private volatile Channel channel;
	private String respMsg;

//
//	@Override
//	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		super.channelActive(ctx);
//		channel = ctx.channel();
//	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		try {
			respMsg = msg.toString(StandardCharsets.UTF_8);
			System.out.println(respMsg);
		} finally {
			ctx.close();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
