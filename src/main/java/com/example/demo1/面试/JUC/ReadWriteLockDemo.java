package com.example.demo1.面试.JUC;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//读写锁
//缺点：饥饿问题，
class MyCache {
    //资源类
    private Map<String, Object> map = new HashMap<>();
    //读写锁
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    //写方法
    public void write(String key, Object value) {

        map.put(key, value);
    }

    //读方法
    public Object read(String key) {

        return map.get(key);
    }

}

//写写不可以同时
//读写不可以同时,写锁降级，但是读锁不能升级
//读读可以同时
public class ReadWriteLockDemo {

}
