package com.springframework.beans;

/**
 * 单例工厂的顶层设计
 * Created by shenyiwei on 2019/4/23.
 */
public interface BeanFactory {

    /**
     * 从IOC容器中获得一个实例Bean
     * @param name
     * @return
     */
    Object getBean(String name);

}
