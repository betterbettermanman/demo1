package com.example.demo1.面试.JUC;

public class SynchronizedDemo {
    private int number = 30;

    private boolean isBuy() {
        if (number == 0) {
            return true;
        }
        return false;
    }

    private void buy() {
        number--;
    }

    public void setId() {
        System.out.println("-----");
    }
}
