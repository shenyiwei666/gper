package com.springframework.aop.framework;

/**
 * Created by shenyiwei on 2019/4/25.
 */
public interface AopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);

}
