package com.example.demo1.面试.JUC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*线程间定制化通信* */

class Print {
    private Lock lock = new ReentrantLock();
    private Condition a = lock.newCondition();
    private Condition b = lock.newCondition();
    private Condition c = lock.newCondition();
    private int state = 1;

    public void printA() {
        lock.lock();
        try {
            while (state != 1) {
                a.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("AA");
            }
            state = 2;
            b.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void printB() {
        lock.lock();
        try {
            while (state != 2) {
                b.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println("BB");
            }
            state = 3;
            c.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            while (state != 3) {
                c.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println("CC");
            }
            state = 1;
            a.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

public class ThreadDemo1 {
    public static void main(String[] args) {
        Print print = new Print();
        new Thread(() -> {
            while (true) {
                print.printA();
            }
        }, "AA").start();
        new Thread(() -> {
            while (true) {
                print.printB();
            }
        }, "BB").start();
        new Thread(() -> {
            while (true) {
                print.printC();
            }
        }, "CC").start();
    }
}
