package com.example.demo1.面试.JUC;

import java.util.HashMap;

public class HashMapDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true) {

            }
        });
        thread.start();
        System.out.println("main is over");
    }

    private static void extracted() {
        HashMap<Object, Object> map = new HashMap<>();
        for (int i = 0; i <= 10000; i++) {
            final int num = i;
            new Thread(() -> {
                map.put(num + "", num + "");

            }, String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().isDaemon());
        System.out.println(map.toString());
    }
}
