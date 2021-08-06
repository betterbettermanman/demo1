package com.example.demo1.面试.JVM;

import java.nio.ByteBuffer;

/**
 * ByteBuffer.allocate(capability),分配内存在JVM堆空间
 * ByteBuffer.allocateDirect(capability),分配OS本地内存，
 */

public class DirectBufferMemoryDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("配置的maxDirectMemory:" + (sun.misc.VM.maxDirectMemory() / 1024 / 1024) + "MB");
        Thread.sleep(3000);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }
}
//IO多路复用,单线程接受，