package com.example.demo1.面试.JVM;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

 class Apple {

    private String name;

    public Apple(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 覆盖finalize，在回收的时候会执行。
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("Apple： " + name + " finalize。");
    }

    @Override
    public String toString() {
        return "Apple{" +
                "name='" + name + '\'' +
                '}' + ", hashCode:" + this.hashCode();
    }
}
public class Client2 {
    public static void main(String[] args) {
        ReferenceQueue<Apple> appleReferenceQueue = new ReferenceQueue<>();
        WeakReference<Apple> appleWeakReference = new WeakReference<Apple>(new Apple("青苹果"), appleReferenceQueue);
        WeakReference<Apple> appleWeakReference2 = new WeakReference<Apple>(new Apple("毒苹果"), appleReferenceQueue);

        System.out.println("=====gc调用前=====");
        Reference<? extends Apple> reference = null;
        while ((reference = appleReferenceQueue.poll()) != null ) {
            //不会输出，因为没有回收被弱引用的对象，并不会加入队列中
            System.out.println(reference);
        }
        System.out.println(appleWeakReference);
        System.out.println(appleWeakReference2);
        System.out.println(appleWeakReference.get());
        System.out.println(appleWeakReference2.get());

        System.out.println("=====调用gc=====");
        System.gc();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("=====gc调用后=====");

        //下面两个输出为null,表示对象被回收了
        System.out.println(appleWeakReference.get());
        System.out.println(appleWeakReference2.get());

        //输出结果，并且就是上面的appleWeakReference、appleWeakReference2，再次证明对象被回收了
        Reference<? extends Apple> reference2 = null;
        while ((reference2 = appleReferenceQueue.poll()) != null ) {
            //如果使用继承的方式就可以包含其他信息了
            System.out.println("appleReferenceQueue中：" + reference2);
        }
    }
}
