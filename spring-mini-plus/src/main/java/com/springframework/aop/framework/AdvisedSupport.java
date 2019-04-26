package com.springframework.aop.framework;

import com.springframework.aop.aspect.AfterReturningAdviceInterceptor;
import com.springframework.aop.aspect.AfterThrowingAdviceInterceptor;
import com.springframework.aop.aspect.MethodBeforeAdviceInterceptor;
import com.springframework.aop.config.AopConfig;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shenyiwei on 2019/4/25.
 */
public class AdvisedSupport {

    private Class<?> targetClass;
    private Object target;
    private AopConfig config;
    private Pattern pointCutClassPattern;
    private transient Map<Method, List<Object>> methodCache;

    public AdvisedSupport(AopConfig config) {
        this.config = config;
    }

    private void parse() {
        String pointCut = this.config.getPointCut()
                .replaceAll("\\.", "\\\\.")
                .replaceAll("\\\\.\\*", ".*")
                .replaceAll("\\(", "\\\\(")
                .replaceAll("\\)", "\\\\)");
        // public .* com.demo.service..*Service..*(.*)
        String pointCutForClassRegex = pointCut.substring(0, pointCut.lastIndexOf("\\(") - 4);
        this.pointCutClassPattern = Pattern.compile("class " +
                pointCutForClassRegex.substring(pointCutForClassRegex.lastIndexOf(" ") + 1));

        Pattern pattern = Pattern.compile(pointCut);
        Class aspectClass = null;
        Map<String, Method> aspectMethods = new HashMap<>();
        this.methodCache = new HashMap<>();


        try {
            aspectClass = Class.forName(this.config.getAspectClass());
            for (Method m : aspectClass.getMethods()) {
                aspectMethods.put(m.getName(), m);
            }

            for (Method m : this.targetClass.getMethods()) {
                String methodString = m.toString();
                if (methodString.contains("throws")) {
                    methodString = methodString.substring(0, methodString.lastIndexOf("throws")).trim();
                }
                Matcher matcher = pattern.matcher(methodString);
                if (matcher.matches()) {
                    // 执行器链
                    List<Object> advices = new LinkedList<>();
                    if (config.getAspectBefore() != null && !"".equals(config.getAspectBefore())) {
                        advices.add(new MethodBeforeAdviceInterceptor(aspectMethods.get(config.getAspectBefore()), aspectClass.newInstance()));
                    }
                    if (config.getAspectAfter() != null && !"".equals(config.getAspectAfter())) {
                        advices.add(new AfterReturningAdviceInterceptor(aspectMethods.get(config.getAspectAfter()), aspectClass.newInstance()));
                    }
                    if (config.getAspectAfterThrow() != null && !"".equals(config.getAspectAfterThrow())) {
                        AfterThrowingAdviceInterceptor afterThrowingAdviceInterceptor = new AfterThrowingAdviceInterceptor(
                                aspectMethods.get(config.getAspectAfterThrow()),
                                aspectClass.newInstance());
                        afterThrowingAdviceInterceptor.setThrowName(config.getAspectAfterThrowingName());
                        advices.add(afterThrowingAdviceInterceptor);
                    }
                    methodCache.put(m, advices);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        parse();
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
        List<Object> advices = this.methodCache.get(method);
        if (advices == null) {
            try {
                Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());
                advices = methodCache.get(m);
                this.methodCache.put(m, advices);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return advices;
    }

    public boolean pointCutMatch() {
        return this.pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }
}
