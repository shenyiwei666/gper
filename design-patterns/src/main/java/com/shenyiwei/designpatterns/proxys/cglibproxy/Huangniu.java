package com.shenyiwei.designpatterns.proxys.cglibproxy;

import com.shenyiwei.designpatterns.proxys.suport.WhiteVerify;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by shenyiwei on 2019-5-7 007.
 */
public class Huangniu implements MethodInterceptor {

    public Object getInstance(Class clazz) {
//        System.out.println("cglib proxy");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 走关系购买内部票
        before();
        // 购票
        methodProxy.invokeSuper(o, objects);
        return null;
    }

    /**
     * 走关系购买内部票
     */
    private void before() {
        WhiteVerify.setWhite(true);
    }
}
