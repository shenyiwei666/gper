package com.springframework.aop.framework;

/**
 * Created by shenyiwei on 2019/4/25.
 */
public class CglibAopProxy implements AopProxy {

    public CglibAopProxy(AdvisedSupport advisedSupport) {

    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
