package com.springframework.aop.aspect;

import com.springframework.aop.framework.ReflectiveMethodInvocation;
import com.springframework.aop.intercept.MethodInterceptor;

import java.lang.reflect.Method;

/**
 * Created by shenyiwei on 2019/4/26.
 */
public class MethodBeforeAdviceInterceptor extends AbstractAspectAdvice implements MethodInterceptor {

    private JoinPoint joinPoint;

    public MethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(ReflectiveMethodInvocation mi) throws Throwable {
        this.joinPoint = mi;
        before(mi.getMethod(), mi.getArguments(), mi.getThis());
        return mi.proceed();
    }

    private void before(Method method, Object[] args, Object target) {
        super.invokeAdviceMethod(this.joinPoint, null, null);
    }
}
