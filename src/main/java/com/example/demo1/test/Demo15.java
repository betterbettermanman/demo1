package com.example.demo1.test;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo15 {

    static Lock lock = new ReentrantLock();
    static int count = 0;
    static Condition conditionA = lock.newCondition();// 必须配合lock.lock()使用
    static Condition conditionB = lock.newCondition();// 必须配合lock.lock()使用

    public static void main(String[] args) {
        System.out.println(new Random(121).nextInt());
        System.out.println(ThreadLocalRandom.current().nextInt(50));
    }
    public synchronized static void  method2(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---------------------");
        new Random(121).nextInt();
    }

}