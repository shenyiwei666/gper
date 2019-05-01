package com.shenyiwei.designpatterns.singletons;

import java.io.Serializable;

/**
 * 饿汉单例模式
 * Created by shenyiwei on 2019-5-1 001.
 */
public class HungrySingleton implements Serializable {

    private final static HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() {
        /* 防止反射破坏单例 */
        if (instance != null) {
            throw new RuntimeException("不允许多次实例化");
        }
        System.out.println(Thread.currentThread().getName() + "------------ new instance");
    }

    /**
     * 获取单例对象
     * @return
     */
    public static HungrySingleton getInstance() {
        return instance;
    }

    /**
     * 防止序列化破坏单例模式，FileInputStream.readObject时会回调
     * @return
     */
    public Object readResolve() {
        return instance;
    }

}
