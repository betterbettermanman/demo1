package com.example.demo1.面试.JUC;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/*
 * 1.不需要在锁中调用
 * 2.不需要先park，再unpark，可以先获取凭证，再阻塞
 * */
public class LockSupportDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("coming in ........");
            LockSupport.park();
            System.out.println("被唤醒。。。。。。");
        }, "A");
        thread.start();
        Thread b = new Thread(() -> {
            LockSupport.unpark(thread);
            System.out.println("颁发凭证");
        }, "B");
        b.start();
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("main is over");
        System.out.println(Runtime.getRuntime().availableProcessors());
        int core = Runtime.getRuntime().availableProcessors();
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        new ThreadPoolExecutor(core, core, 1L, TimeUnit.SECONDS, arrayBlockingQueue, ThreadFactoryBuilder.create().build(), new ThreadPoolExecutor.AbortPolicy());

    }

}

