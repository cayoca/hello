package com.geek.java.week4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
public class Homework04 {

    public static volatile int result ;
    
    
    public static void main(String[] args) throws InterruptedException {
    	
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池
        final ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Task task = new Task(lock, condition);
        task.start();

        lock.lock();
        condition.await(1, TimeUnit.SECONDS);

        
        // 确保拿到result并输出 
        System.out.println(Thread.currentThread().getName() + ": 异步计算结果为：" + result);
        
        lock.unlock();
        
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
    	private final ReentrantLock lock;
    	private final Condition condition;
    	public Task(ReentrantLock lock, Condition condition) {
    		super();
    		this.lock = lock;
    		this.condition = condition;
    	}
		@Override
		public void run() {
	        // 异步执行 下面方法
			lock.lock();
			result = sum();
			System.out.println(currentThread().getName() +": result="+result);
			condition.signal();
			lock.unlock();
		}
    	
    }
}
