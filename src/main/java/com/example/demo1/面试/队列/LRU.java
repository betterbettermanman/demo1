package com.example.demo1.面试.队列;

import java.util.HashMap;

//双链表
class Node {
    public int key, val;
    public Node pre, next;

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }
}

class DoubleList {
    private Node tail, head;
    private int size;

    public DoubleList() {
    }

    // 在链表头部添加节点 x
    public void addFirst(Node x) {
        if (head == null) {
            head = x;
            tail = x;
        } else {
            Node temp = tail;
            temp.pre = x;
            x.next = temp;
            head = x;
        }
        size++;
    }


    // 删除链表中的 x 节点（x 一定存在）
    public void remove(Node x) {
        if (x == head) {
            x.next.pre = null;
            head = x.next;
        } else if (x == tail) {
            x.pre.next = null;
            tail = x.pre;
        } else {
            x.pre.next = x.next;
            x.next.pre = x.pre;
        }
        size--;
    }

    // 删除链表中最后一个节点，并返回该节点
    public Node removeLast() {
        Node result = tail;
        tail.pre.next = null;
        tail = tail.pre;
        size--;
        return result;
    }

    // 返回链表长度
    public int size() {
        return size;
    }
}

/*
 * 实现LRU算法
 * */
public class LRU {
    private HashMap<Integer, Node> map;
    private DoubleList cache;
    //最大容量
    private int cap;

    public LRU(int cap) {
        map = new HashMap<>(cap);
        cache = new DoubleList();
        this.cap = cap;
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        } else {
            return node.val;
        }
    }

    public void put(int key, int val) {
        Node node = new Node(key, val);
        if (map.containsKey(key)) {
            map.remove(key);
            cache.remove(node);
        } else {
            if (cap == map.size()) {
                Node last = cache.removeLast();
                map.remove(last.key);
            }
        }
        map.put(key, node);
        cache.addFirst(node);
    }

}
