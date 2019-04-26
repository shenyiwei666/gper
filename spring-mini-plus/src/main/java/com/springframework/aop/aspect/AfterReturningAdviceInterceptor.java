package com.springframework.aop.aspect;

import com.springframework.aop.framework.ReflectiveMethodInvocation;
import com.springframework.aop.intercept.MethodInterceptor;

import java.lang.reflect.Method;

/**
 * Created by shenyiwei on 2019/4/26.
 */
public class AfterReturningAdviceInterceptor extends AbstractAspectAdvice implements MethodInterceptor {

    private JoinPoint joinPoint;

    public AfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(ReflectiveMethodInvocation mi) throws Throwable {
        this.joinPoint = mi;
        Object retVal = mi.proceed();
        afterReturning(retVal, mi.getMethod(), mi.getArguments(), mi.getThis());
        return retVal;
    }

    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) {
        super.invokeAdviceMethod(this.joinPoint, retVal, null);
     }
}
