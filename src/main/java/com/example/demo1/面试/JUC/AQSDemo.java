package com.example.demo1.面试.JUC;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyAQS extends AbstractQueuedSynchronizer{

}
public class AQSDemo {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        Semaphore semaphore = new Semaphore(5);
        ReentrantLock reentrantLock = new ReentrantLock();
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    }
}
