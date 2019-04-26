package com.springframework.aop.intercept;

import com.springframework.aop.framework.ReflectiveMethodInvocation;

/**
 * Created by shenyiwei on 2019/4/25.
 */
public interface MethodInterceptor {

    Object invoke(ReflectiveMethodInvocation invocation) throws Throwable;

}
