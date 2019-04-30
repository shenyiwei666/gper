package com.shenyiwei.designpatterns.factorys.factorymethod.factory;

import com.shenyiwei.designpatterns.factorys.factorymethod.bean.Phone;

/**
 * Created by shenyiwei on 2019-4-30 030.
 */
public class PhoneFactory implements ProductFactory {
    @Override
    public Phone create() {
        return new Phone();
    }
}
