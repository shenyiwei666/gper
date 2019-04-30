package com.shenyiwei.designpatterns.factory.abstractfactory.factory;

import com.shenyiwei.designpatterns.factory.abstractfactory.bean.*;

/**
 * Created by shenyiwei on 2019-4-30 030.
 */
public class HuaweiProductFactory implements ProductFactory {
    @Override
    public Phone createPhone() {
        return new HuaweiPhone();
    }

    @Override
    public Pad createPad() {
        return new HuaweiPad();
    }

    @Override
    public Laptop createLaptop() {
        return new HuaweiLaptop();
    }
}
