package com.shenyiwei.designpatterns.proxys.jdkproxy;

import com.shenyiwei.designpatterns.proxys.suport.WhiteVerify;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by shenyiwei on 2019-5-7 007.
 */
public class Huangniu implements InvocationHandler {
    private Object target;

    public Object getInstance(Class clazz) {
//        System.out.println("jdk proxy");
        try {
            this.target = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 走关系购买内部票
        before();
        // 购票
        method.invoke(target, args);
        return null;
    }

    /**
     * 走关系购买内部票
     */
    private void before() {
        WhiteVerify.setWhite(true);
    }
}
