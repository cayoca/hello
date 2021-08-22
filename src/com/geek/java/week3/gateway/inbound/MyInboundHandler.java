package com.geek.java.week3.gateway.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

import com.geek.java.week3.gateway.filter.MyRequestFilter;
import com.geek.java.week3.gateway.filter.MyResponseFilter;
import com.geek.java.week3.gateway.outbound.MyOutboundHandler;
import com.geek.java.week3.gateway.router.MyRouter;

public class MyInboundHandler extends ChannelInboundHandlerAdapter {
	
    private MyOutboundHandler myOutboundHandler;
    
    public MyInboundHandler() {
    	super();
    	MyRouter router = new MyRouter();
    	MyRequestFilter myReqFilter = new MyRequestFilter();
    	MyResponseFilter myRespFilter = new MyResponseFilter();
    	this.myOutboundHandler = new MyOutboundHandler(router,myReqFilter,myRespFilter);
    }

	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
        	//转发
            myOutboundHandler.handle(ctx,(FullHttpRequest) msg);
    
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
