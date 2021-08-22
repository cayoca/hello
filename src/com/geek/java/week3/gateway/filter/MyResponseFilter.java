package com.geek.java.week3.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

public class MyResponseFilter {

	public void doFilter(FullHttpResponse response) {
		response.headers().set("kk", "java-1-nio");
		
	}

}
