package com.example.demo1.面试.JVM;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/*
1.Xss:ThreadStackSize
2.Xms:InitialHeapSize
3.Xmx:MaxHeapSize
4.Xmn:年轻代大小
5.-XX:MetaspaceSize
6.-XX:+PrintGCDetails   打印GC详情
7.-XX:SurvivorRatio     设置新生代中eden和S0/S1空间得比例
8.-XX:NewRatio          设置新生代和老年代得空间比例
9.-XX:MaxTenuringThreshold  垃圾最大年龄
* */
public class HelloGC {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void extracted() throws InterruptedException {
        System.out.println("***********HelloGc");
        Thread.sleep(Integer.MAX_VALUE);
        System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024);
        System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024);
        ArrayList<Object> objects = new ArrayList<>();
        SoftReference<List> integerSoftReference = new SoftReference<List>(objects);
        integerSoftReference.get();
    }

    public static void softRef_Memory_Enough() throws InterruptedException {
        Object o = new Object();
        SoftReference<Object> soft = new SoftReference<>(o);
        System.out.println(o);
        System.out.println(soft.get());
        o = null;
        try {
            byte[] b = new byte[1024 * 1024 * 1000];
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println(o);
            System.out.println(soft.get());
        }
    }

}
/*
引用分类：强引用，软引用（当内存不足，就会被gc回收），弱引用（只要发生gc，就会被回收）
*
*
* */