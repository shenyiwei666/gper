package com.springframework.aop.aspect;

import java.lang.reflect.Method;

/**
 * Created by shenyiwei on 2019/4/26.
 */
public abstract class AbstractAspectAdvice implements Advice {
    private Method aspectMethod;
    private Object aspectTarget;

    public AbstractAspectAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    public Object invokeAdviceMethod(JoinPoint joinPoint, Object returnValue, Throwable tx) {
        try {
            Class[] paramTypes = this.aspectMethod.getParameterTypes();
            if (paramTypes == null || paramTypes.length == 0) {
                return this.aspectMethod.invoke(aspectTarget);
            } else {
                Object[] args = new Object[paramTypes.length];
                for (int i = 0; i < args.length; i++) {
                    Class clazz = paramTypes[i];
                    if (clazz == JoinPoint.class) {
                        args[i] = joinPoint;
                    } else if (clazz == Throwable.class) {
                        args[i] = tx;
                    } else if (clazz == Object.class) {
                        args[i] = returnValue;
                    }
                }
                return this.aspectMethod.invoke(aspectTarget, args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
