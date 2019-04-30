package com.shenyiwei.designpatterns.factory.simplefactory;

/**
 * Created by shenyiwei on 2019-4-30 030.
 */
public class SimpleFactory {

    public static Object create(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
