package com.example.demo1.面试.JUC;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LTicket {
    //票数
    private int number = 30;
    //锁对象
    private final Lock lock = new ReentrantLock();

    //卖票
    public boolean sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + ":: 卖出：" + (number--) + "剩余：" + number);
                return true;
            }
        } finally {
            lock.unlock();
        }
        return false;
    }
}

public class LSaleTicket {
    //创建3个线程，调用资源类
    public static void main(String[] args) {
        LTicket ticket = new LTicket();
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                while (ticket.sale()) {

                }
            }, String.valueOf(i)).start();
        }
    }
}
