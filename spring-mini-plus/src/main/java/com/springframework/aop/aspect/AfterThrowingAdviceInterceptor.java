package com.springframework.aop.aspect;

import com.springframework.aop.framework.ReflectiveMethodInvocation;
import com.springframework.aop.intercept.MethodInterceptor;

import java.lang.reflect.Method;

/**
 * Created by shenyiwei on 2019/4/26.
 */
public class AfterThrowingAdviceInterceptor extends AbstractAspectAdvice implements MethodInterceptor {

    private String throwName;

    public AfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(ReflectiveMethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        } catch (Throwable e) {
            super.invokeAdviceMethod(mi, null, e);
            throw e;
        }
    }

    public void setThrowName(String throwName) {
        this.throwName = throwName;
    }
}
