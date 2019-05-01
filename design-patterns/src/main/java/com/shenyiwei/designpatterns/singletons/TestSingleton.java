package com.shenyiwei.designpatterns.singletons;

/**
 * Created by shenyiwei on 2019-5-1 001.
 */
public class TestSingleton {

    public static void main(String[] args) {
        //      单例模式        是否重复创建
        // HungrySingleton      ×
        // LazySingleton        ×
        // InnerClassSingleton  ×
        // EnumSingleton        ×

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() +" ----- "+
                            InnerClassSingleton.getInstance());
                }
            }).start();
        }

    }

}
