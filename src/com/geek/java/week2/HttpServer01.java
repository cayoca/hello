package com.geek.java.week2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer01 {
	public static void main(String[] args) throws IOException  {
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(8801);
		while(true) {
			Socket socket;
			try {
				System.out.println("Server Start!");
				socket = server.accept();
				service(socket);
				System.out.println("work");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
							
			}

		}	
		
	}

	private static void service(Socket socket) {
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
			pw.println("HTTP/1.1 200 OK");
			pw.println("Content-Type:text/html;charset=utf-8");
			String body = "hello,world01";
			pw.println("Content-Length:"+body.getBytes().length);
			pw.println();
			pw.write(body);
			pw.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
