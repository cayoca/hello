package com.geek.java.week3.gateway.filter;

import io.netty.handler.codec.http.FullHttpRequest;

public class MyRequestFilter {

	public void doFilter(FullHttpRequest fullRequest) {
		fullRequest.headers().set("mao", "soul");
	}

}
