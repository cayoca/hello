package com.geek.java.week3.gateway.router;

import java.util.Random;

public class MyRouter {

	private final String[] urls = { "http://localhost:8801", "http://localhost:8801" };
	public String getBackendUrl() {
		return urls[new Random().nextInt(urls.length)];
	}

}
