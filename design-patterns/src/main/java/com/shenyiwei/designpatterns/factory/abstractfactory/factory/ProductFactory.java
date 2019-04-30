package com.shenyiwei.designpatterns.factory.abstractfactory.factory;

import com.shenyiwei.designpatterns.factory.abstractfactory.bean.Laptop;
import com.shenyiwei.designpatterns.factory.abstractfactory.bean.Pad;
import com.shenyiwei.designpatterns.factory.abstractfactory.bean.Phone;

/**
 * Created by shenyiwei on 2019-4-30 030.
 */
public interface ProductFactory {

    Phone createPhone();

    Pad createPad();

    Laptop createLaptop();

}
