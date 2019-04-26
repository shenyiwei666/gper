package com.springframework.aop.aspect;

import java.lang.reflect.Method;

/**
 * Created by shenyiwei on 2019/4/26.
 */
public interface JoinPoint {

    Object getThis();

    Object[] getArguments();

    Method getMethod();

    void setUserAttribute(String key, Object value);

    Object getUserAttribute(String key);

}
