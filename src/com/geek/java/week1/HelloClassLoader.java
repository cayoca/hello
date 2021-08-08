package com.geek.java.week1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;

public class HelloClassLoader extends ClassLoader {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		byte[] bytes = readClassFile("res/Hello.xlass");
		trans(bytes);
		
		HelloClassLoader helloClassLoader = new HelloClassLoader();
		Class<?> clz = helloClassLoader.defineClass("Hello",bytes,0,bytes.length);
		clz.getMethod("hello").invoke(clz.newInstance());
				
	}
	
	private static void trans(byte[] bytes) {
		for(int i=0;i<bytes.length;i++) {
			bytes[i] = (byte) (255-bytes[i]);
		}
	}

	
	private static byte[] readClassFile(String path) {
		File file = new File(path);
		byte[] bytes = new byte[(int)file.length()];
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            bytes = in.readAllBytes();
            in.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
	}
	

}
