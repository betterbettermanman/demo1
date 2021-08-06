package com.example.demo1.面试.JVM;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class PhantomReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        PhantomReference<Object> objectPhantomReference = new PhantomReference<>(o, queue);
        System.out.println(o);
        System.out.println(objectPhantomReference.get());
        System.out.println(queue.poll());
        System.out.println("==============");
        o=null;
        System.gc();
        Thread.sleep(1000);
        System.out.println(o);
        System.out.println(objectPhantomReference.get());
        System.out.println(queue.poll());

    }
}
//GCRoot:虚拟机栈中变量对象，方法区常量对象，方法区静态变量，本地方法栈中的对象
//引用类型：强，软，弱，虚，