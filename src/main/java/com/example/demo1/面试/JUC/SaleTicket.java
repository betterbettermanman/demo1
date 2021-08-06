package com.example.demo1.面试.JUC;

class Ticket {
    //票数
    private int number = 30;

    //卖票
    public synchronized boolean sale() {
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + ":: 卖出：" + (number--) + "剩余：" + number);
            return true;
        }
        return false;
    }

}

public class SaleTicket {
    //创建3个线程，调用资源类
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                while (ticket.sale()) {

                }
            }, String.valueOf(i)).start();
        }
    }
}
