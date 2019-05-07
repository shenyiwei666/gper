package com.shenyiwei.designpatterns.proxys.myproxy.core;

import java.lang.reflect.Method;

/**
 * Created by shenyiwei on 2019-5-7 007.
 */
public interface MyInvocationHandler {

    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;

}
