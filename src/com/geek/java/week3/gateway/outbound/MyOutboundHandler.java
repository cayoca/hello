package com.geek.java.week3.gateway.outbound;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;

import com.geek.java.week3.gateway.filter.MyRequestFilter;
import com.geek.java.week3.gateway.filter.MyResponseFilter;
import com.geek.java.week3.gateway.router.MyRouter;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;

public class MyOutboundHandler {
	final CloseableHttpClient httpclient = HttpClients.createDefault();
	private ExecutorService executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(),
            1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1000),
            new ThreadPoolExecutor.CallerRunsPolicy());
	
	private MyRouter myRouter;
	private MyRequestFilter myRequestFilter;
	private MyResponseFilter myResponseFilter;

	
	public MyOutboundHandler(MyRouter myRouter, MyRequestFilter myRequestFilter, MyResponseFilter myResponseFilter) { 
		this.myRouter = myRouter;
		this.myRequestFilter = myRequestFilter;
		this.myResponseFilter = myResponseFilter;
	}
	
	public void handle(ChannelHandlerContext ctx, FullHttpRequest fullRequest) {

		// filter
		myRequestFilter.doFilter(fullRequest);
		
		// router
		String backendUrl = myRouter.getBackendUrl();
		@SuppressWarnings("deprecation")
		String url = backendUrl + fullRequest.getUri();

		// get
		executor.execute(() -> {
			httpget(url, ctx, fullRequest);			
		});

	}

	private void httpget(String url, ChannelHandlerContext ctx, FullHttpRequest fullRequest) {

		FullHttpResponse fullResponse = null;

		final HttpGet httpget = new HttpGet(url);
		try (final CloseableHttpResponse response = httpclient.execute(httpget)) {
			final HttpEntity entity = response.getEntity();
			final InputStream inStream = entity.getContent();
			byte[] bytes = inStream.readAllBytes();
			System.out.println("["+Thread.currentThread().getName()+"] Executing request " + httpget.getMethod() + " " + httpget.getUri() + "  result = "
					+ new String(bytes, "UTF-8"));

			fullResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(bytes));
			fullResponse.headers().set("Content-Type", "application/json");
			fullResponse.headers().setInt("Content-Length", fullResponse.content().readableBytes());

			// filter
			myResponseFilter.doFilter(fullResponse);
		} catch (Exception e) {
			e.printStackTrace();
			fullResponse = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
		} finally {
			if (fullRequest == null) {
				return;
			}
			if (!HttpUtil.isKeepAlive(fullRequest)) {
				ctx.write(fullResponse).addListener(ChannelFutureListener.CLOSE);
			} else {
				// response.headers().set(CONNECTION, KEEP_ALIVE);
				ctx.write(fullResponse);
			}
			ctx.flush();
		}

	}

}
