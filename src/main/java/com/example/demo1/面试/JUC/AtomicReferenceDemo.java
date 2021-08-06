package com.example.demo1.面试.JUC;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        ArrayBlockingQueue queue = new ArrayBlockingQueue<String>(10);
        new Thread(() -> {
            try {
                while (true) {
                    queue.put("caicai");
                    System.out.println("生产");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "生产").start();
        new Thread(() -> {
            try {
                while (true) {
                    queue.take();
                    System.out.println("消费");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "消费").start();
    }

    private static void extracted() {
        AtomicReference<Integer> objectAtomicReference = new AtomicReference<>();
        objectAtomicReference.set(0);
        objectAtomicReference.compareAndSet(8, 9);
        System.out.println(objectAtomicReference.get());
        AtomicStampedReference<Integer> objectAtomicStampedReference = new AtomicStampedReference<>(1, 1);
        objectAtomicStampedReference.compareAndSet(1, 9, 3, 2);
        System.out.println(objectAtomicStampedReference.getReference());
        //可见性，原子性，有序性。
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    private static String lock1 = "loc1";
    private static String lock2 = "loc2";

    public static void method1() {
        new Thread(() -> {
            synchronized (lock1) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    synchronized (lock2) {

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (lock2) {
                synchronized (lock1) {

                }

            }
        }).start();
    }


}
