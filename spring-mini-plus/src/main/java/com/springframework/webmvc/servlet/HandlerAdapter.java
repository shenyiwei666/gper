package com.springframework.webmvc.servlet;

import com.springframework.stereotype.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by shenyiwei on 2019/4/24.
 */
public class HandlerAdapter {

    public boolean supports(Object handler) {
        return (handler instanceof HandlerMapping);
    }

    public Object handle(HttpServletRequest req, HttpServletResponse resp, Object handler)
            throws Exception {
        HandlerMapping hm = (HandlerMapping) handler;
        Method method = hm.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] paramValues = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            Class clazz = param.getClass();
            Annotation[] annotations = param.getAnnotations();
            if (clazz == HttpServletRequest.class) {
                paramValues[i] = req;
            } else if (clazz == HttpServletResponse.class) {
                paramValues[i] = resp;
            } else {
                String paramName = null;
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType() == RequestParam.class) {
                        RequestParam requestParam = (RequestParam) annotation;
                        paramName = requestParam.value();
                        break;
                    }
                }

                if (paramName != null) {
                    try {
                        paramValues[i] = convert(req.getParameter(paramName), param.getType());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    paramValues[i] = null;
                }
            }
        }
        Object result = method.invoke(hm.getController(), paramValues);
        if (result == null || result instanceof Void) {
            return null;
        }
        if (method.getReturnType() == ModelAndView.class) {
            return (ModelAndView) result;
        }
        return result;
    }

    private Object convert(String value, Class type) throws IllegalAccessException {
        if (value == null) {
            return null;
        }
        if (type == String.class) {
            return value;
        } else if (type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == Float.class) {
            return Float.parseFloat(value);
        } else if (type == Long.class) {
            return Long.parseLong(value);
        } else {
            throw new IllegalAccessException(" Data type is not supported, " + value);
        }
    }

}
