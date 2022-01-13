package com.example.demo1.面试.队列;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

/**
 * 顺序读，顺序写和零拷贝
 */
public class Read_Write {
    /**
     * 顺序写
     *
     * @param filePath
     * @param content
     * @param index
     * @return
     */
    public static long fileWrite(String filePath, String content, int index) {
        File file = new File(filePath);
        RandomAccessFile randomAccessTargetFile;
        //  操作系统提供的一个内存映射的机制的类
        MappedByteBuffer map;
        try {
            randomAccessTargetFile = new RandomAccessFile(file, "rw");
            FileChannel targetFileChannel = randomAccessTargetFile.getChannel();
            map = targetFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, (long) 1024 * 1024);
            map.position(index);
            map.put(content.getBytes());
            return map.position();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return 0L;
    }

    /*public static void main(String[] args) {
        fileWrite("D:/a.txt", "132313131", 1);
    }*/

    /**
     * 顺序读
     *
     * @param filePath
     * @param index
     * @return
     */
    public static String fileRead(String filePath, long index) {
        File file = new File(filePath);
        RandomAccessFile randomAccessTargetFile;
        //  操作系统提供的一个内存映射的机制的类
        MappedByteBuffer map;
        try {
            randomAccessTargetFile = new RandomAccessFile(file, "rw");
            FileChannel targetFileChannel = randomAccessTargetFile.getChannel();
            map = targetFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, index);
            byte[] byteArr = new byte[10 * 1024];
            map.get(byteArr, 0, (int) index);
            return new String(byteArr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return "";
    }

    /**
     * 零拷贝
     *
     * @param filePath 文件地址
     * @param hostName 目标ip
     * @param port     目标端口
     * @return
     */
    public static void zero_copy(String filePath, String hostName, int port) {
        File file = new File(filePath);
        RandomAccessFile randomAccessFile = null;
        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(hostName, port));
            randomAccessFile = new RandomAccessFile(file, "rw");
            FileChannel fileChannel = randomAccessFile.getChannel();
            // 通道传输，不经过进程的内核空间的拷贝，直接由缓存拷贝到套接字上
            fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HashMap<Object, Object> map = new HashMap<>(2);
        map.put("hello","1231");
        map.put("hello1","1231");
        map.put("hello2","1231");
        map.put("hello3","1231");
        map.put("hello4","1231");
        map.put("hello5","1231");
        map.put("hello6","1231");
        map.put("hello7","1231");
        map.put("hello8","1231");
        map.put("hello9","1231");
        map.put("hello0","1231");
    }
}
