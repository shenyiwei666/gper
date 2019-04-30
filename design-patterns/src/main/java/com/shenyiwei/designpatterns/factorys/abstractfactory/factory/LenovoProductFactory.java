package com.shenyiwei.designpatterns.factorys.abstractfactory.factory;

import com.shenyiwei.designpatterns.factorys.abstractfactory.bean.*;

/**
 * Created by shenyiwei on 2019-4-30 030.
 */
public class LenovoProductFactory implements ProductFactory {
    @Override
    public Phone createPhone() {
        return new LenovoPhone();
    }

    @Override
    public Pad createPad() {
        return new LenovoPad();
    }

    @Override
    public Laptop createLaptop() {
        return new LenovoLaptop();
    }
}
