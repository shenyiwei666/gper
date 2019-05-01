package com.shenyiwei.designpatterns.singletons;

import java.io.Serializable;

/**
 * 枚举单例模式
 * Created by shenyiwei on 2019-5-1 001.
 */
public enum EnumSingleton implements Serializable {
    INSTANCE;

    /**
     * 获取单例对象
     * @return
     */
    public static EnumSingleton getInstance() {
        return INSTANCE;
    }

}
