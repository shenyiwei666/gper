package com.shenyiwei.designpatterns.factory.abstractfactory.factory;

import com.shenyiwei.designpatterns.factory.abstractfactory.bean.*;

/**
 * Created by shenyiwei on 2019-4-30 030.
 */
public class XiaomiProductFactory implements ProductFactory {
    @Override
    public Phone createPhone() {
        return new XiaomiPhone();
    }

    @Override
    public Pad createPad() {
        return new XiaomiPad();
    }

    @Override
    public Laptop createLaptop() {
        return new XiaomiLaptop();
    }
}
