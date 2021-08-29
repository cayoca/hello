package com.geek.java.week4;


/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
public class Homework03 {
    
    public static volatile int tag = 0;
    public static volatile int result ;
    
    public static void main(String[] args) throws InterruptedException {
    	
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池
        Task task = new Task();
        task.start();
        while(tag==0) continue;
        
        // 确保拿到result并输出 
        System.out.println(Thread.currentThread().getName() + ": 异步计算结果为：" + result);
        
        // 然后退出main线程
        System.out.println(Thread.currentThread().getName() + ": 使用时间："+ (System.currentTimeMillis()-start) + " ms");

    }
    
    private static int sum() {
        return fibo(36);
    }
    
    private static int fibo(int a) {
        if ( a < 2) 
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
    
    public static class Task  extends Thread {
		@Override
		public void run() {
	        // 异步执行 下面方法
			result = sum();
			System.out.println(currentThread().getName() +": result="+result);
			tag ++ ;
		}
    	
    }
}
