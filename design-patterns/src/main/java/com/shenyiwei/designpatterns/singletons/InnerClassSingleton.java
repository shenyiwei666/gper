package com.shenyiwei.designpatterns.singletons;

import java.io.Serializable;

/**
 * 静态内部类单例模式
 * Created by shenyiwei on 2019-5-1 001.
 */
public class InnerClassSingleton implements Serializable {

    static {
        System.out.println("static InnerClassSingleton");
    }

    private InnerClassSingleton() {
          /* 防止反射破坏单例 */
        if (Inner.instance != null) {
            throw new RuntimeException("不允许多次实例化");
        }
        System.out.println(Thread.currentThread().getName() + "------------ new instance");
    }

    /**
     * 获取单例对象
     * @return
     */
    public static InnerClassSingleton getInstance() {
        return Inner.instance;
    }

    /**
     * 防止序列化破坏单例模式，FileInputStream.readObject时会回调
     * @return
     */
    public Object readResolve() {
        return Inner.instance;
    }

    // 当外部类调用内部类时，内部类才会被加载，并且内部类是在外部类之前加载
    private static class Inner {
        static {
            System.out.println("static Inner");
        }

        public static InnerClassSingleton instance = new InnerClassSingleton();
    }

}
