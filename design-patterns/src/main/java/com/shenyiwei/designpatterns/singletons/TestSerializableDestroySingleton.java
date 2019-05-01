package com.shenyiwei.designpatterns.singletons;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 通过序列化破坏单例模式
 * Created by shenyiwei on 2019-5-1 001.
 */
public class TestSerializableDestroySingleton {

    public static void main(String[] args) {
        //      单例模式        是否被破坏
        // HungrySingleton      √
        // LazySingleton        √
        // InnerClassSingleton  √
        // EnumSingleton        ×

        EnumSingleton o1 = EnumSingleton.getInstance();
        EnumSingleton o2 = null;

        try {
            FileOutputStream fos = new FileOutputStream("serializable.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(o1);
            oos.flush();
            oos.close();

            FileInputStream fis = new FileInputStream("serializable.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            o2 = (EnumSingleton) ois.readObject();
            ois.close();

            System.out.println(o1);
            System.out.println(o2);
            System.out.println(o1 == o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
