package com.example.demo1.面试.JUC;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException, NoSuchFieldException, IllegalAccessException {
       /* CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 4; i++) {
            new Thread(countDownLatch::countDown).start();
        }
        countDownLatch.await();*/
        method1();
    }

    public static void method1() throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        String str = "Hello Unsafe";
        ArrayList<String> list = new ArrayList<>();
        list.add(str);
        System.out.println(list);
        Field value = str.getClass().getDeclaredField("value");
        unsafe.putObject(str, unsafe.objectFieldOffset(value), new char[]{'M', 'a', 'j', 'i', 'c'});
        System.out.println(str);

        System.out.println("==================");
        System.out.println(list);


    }
}
