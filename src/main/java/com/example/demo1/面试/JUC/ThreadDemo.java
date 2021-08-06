package com.example.demo1.面试.JUC;

class Share {
    private int number = 0;

    public synchronized void incr() throws InterruptedException {
        //判断 干活 通知
        while (number ==20) {
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName() + "生产,number:" + this.number);
        Thread.sleep(1000);
        this.notifyAll();
    }

    public synchronized void decs() throws InterruptedException {
        while (number ==0) {
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "消费,number:" + this.number);
        Thread.sleep(1000);
        this.notifyAll();
    }
}

public class ThreadDemo {
    public static void main(String[] args) {
        Share share = new Share();
        new Thread(() -> {
            try {
                while (true) {
                    share.incr();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "生产者").start();

        new Thread(() -> {
            try {
                while (true) {
                    share.decs();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "消费者").start();
    }


}
