package com.springframework.beans;

/**
 * Created by shenyiwei on 2019/4/23.
 */
public class BeanWrapper {
    public Object wrappedInstance;
    public Class getWrappedClass;

    public BeanWrapper(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    public Object getWrappedInstance() {
        return this.wrappedInstance;
    }

    public Class<?> getWrappedClass() {
        return getWrappedInstance().getClass();
    }

}
