package com.example.demo1.面试.JVM;

import java.util.HashMap;
import java.util.WeakHashMap;

public class WeakHashMapDemo {
    public static void main(String[] args) {
        myHashMap();
        myWeakHashMap();
    }

    private static void myHashMap() {
        HashMap<Integer, String> map = new HashMap<>();
        Integer key = new Integer(1);
        String value = "HashMap";
        map.put(key, value);
        System.out.println(map);
        key=null;
        System.out.println(map);
        System.gc();
        System.out.println(map);
    }
    private static void myWeakHashMap() {
        WeakHashMap<Integer, String> map = new WeakHashMap<>();
        Integer key = new Integer(1);
        String value = "HashMap";
        map.put(key, value);
        System.out.println(map);
        key=null;
        System.out.println(map);
        System.gc();
        System.out.println(map);
    }
}
