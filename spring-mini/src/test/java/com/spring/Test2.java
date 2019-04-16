package com.spring;

import java.lang.reflect.Type;

/**
 * Created by shenyiwei on 2019/4/16.
 */
public class Test2 {

    public static void main(String[] args) {
        try {
            Class clazz = Class.forName("com.demo.service.impl.UserServiceImpl");
            for (Type type : clazz.getGenericInterfaces()) {
                System.out.println(type.getTypeName());
            }
            System.out.println("------------------------");
            for (Class c : clazz.getInterfaces()) {
                System.out.println(c.getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}


