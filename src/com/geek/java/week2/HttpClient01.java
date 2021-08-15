package com.geek.java.week2;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class HttpClient01 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpGet httpget = new HttpGet("http://localhost:8801");

            System.out.println("Executing request " + httpget.getMethod() + " " + httpget.getUri());
            try (final CloseableHttpResponse response = httpclient.execute(httpget)) {
                System.out.println("----------------------------------------");
                System.out.println(response.getCode() + " " + response.getReasonPhrase());

                final HttpEntity entity = response.getEntity();

                if (entity != null) {
                    try (final InputStream inStream = entity.getContent()) {
                    	System.out.println("encoding:"+entity.getContentEncoding() +" length:"+ entity.getContentLength() +" type:"+ entity.getContentType());
                    	System.out.println("trailers:"+entity.getTrailerNames());
                    	System.out.println();
                    	
                    	byte[] bytes = inStream.readAllBytes();
                    	String result = new String(bytes,"UTF-8");
                    	System.out.println("result = "+result);
                    } catch (final IOException ex) {
                        throw ex;
                    }
                }
            }
        }
	}
	
	
	
	public static String run(OkHttpClient client, String url) throws IOException {
		Request request = new Request.Builder().url(url).build();
	    Response response = client.newCall(request).execute();
	    if (response.isSuccessful()) { 
	    	return response.body().string();
	    } else {
	    	throw new IOException("Unexpected code " + response);
	    }
	}
}
