package com.springframework.beans.support;

import com.springframework.beans.factory.config.BeanDefinition;
import com.springframework.context.support.AbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shenyiwei on 2019/4/23.
 */
public class DefaultListableBeanFactory extends AbstractApplicationContext {

    // 存储注册信息的BeanDefinition，伪IOC容器
    protected final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(256);


}
