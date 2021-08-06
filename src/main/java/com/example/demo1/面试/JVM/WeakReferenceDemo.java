package com.example.demo1.面试.JVM;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakReferenceDemo extends WeakReference<Object> {
    private final Object key;

    private WeakReferenceDemo(Object key, Object value, ReferenceQueue<Object> garbageCollectionQueue) {
        super(value, garbageCollectionQueue);
        this.key = key;
    }

    public static void main(String[] args) {
        ReferenceQueue<Object> objectReferenceQueue = new ReferenceQueue<>();
        Object o1 = new Object();
        WeakReferenceDemo weakReferenceDemo = new WeakReferenceDemo("hello", o1, objectReferenceQueue);
        System.out.println(o1);
        System.out.println(objectReferenceQueue.poll());
        o1=null;
        System.gc();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=====================");
        System.out.println(o1);
        System.out.println(objectReferenceQueue.poll());
    }
}
