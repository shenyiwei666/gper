package com.shenyiwei.designpatterns.proxys.myproxy;

import com.shenyiwei.designpatterns.proxys.suport.WhiteVerify;
import com.shenyiwei.designpatterns.proxys.myproxy.core.MyClassLoader;
import com.shenyiwei.designpatterns.proxys.myproxy.core.MyInvocationHandler;
import com.shenyiwei.designpatterns.proxys.myproxy.core.MyProxy;

import java.lang.reflect.Method;

/**
 * Created by shenyiwei on 2019-5-7 007.
 */
public class Huangniu implements MyInvocationHandler {
    private Object target;

    public Object getInstance(Class clazz) {
//        System.out.println("my proxy");
        try {
            this.target = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MyProxy.newProxyInstance(new MyClassLoader(), clazz.getInterfaces(), this);
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
