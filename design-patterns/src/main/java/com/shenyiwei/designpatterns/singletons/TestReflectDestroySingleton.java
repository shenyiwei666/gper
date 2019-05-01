package com.shenyiwei.designpatterns.singletons;

import com.shenyiwei.designpatterns.singletons.EnumSingleton;

import java.lang.reflect.Constructor;

/**
 * 通过反射破坏单例模式
 * Created by shenyiwei on 2019-5-1 001.
 */
public class TestReflectDestroySingleton {

    public static void main(String[] args) {
        //      单例模式        是否被破坏
        // HungrySingleton      √
        // LazySingleton        √
        // InnerClassSingleton  √
        // EnumSingleton        ×

        try {
            Object o1 = EnumSingleton.getInstance();
            System.out.println("--------------------------------------------");

            Class clazz = EnumSingleton.class;
            Constructor cons = clazz.getDeclaredConstructor(null);
            cons.setAccessible(true);
            Object o2 = cons.newInstance();

            System.out.println(o1);
            System.out.println(o2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
