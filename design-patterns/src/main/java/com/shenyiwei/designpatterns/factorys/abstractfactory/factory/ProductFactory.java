package com.shenyiwei.designpatterns.factorys.abstractfactory.factory;

import com.shenyiwei.designpatterns.factorys.abstractfactory.bean.Laptop;
import com.shenyiwei.designpatterns.factorys.abstractfactory.bean.Pad;
import com.shenyiwei.designpatterns.factorys.abstractfactory.bean.Phone;

/**
 * Created by shenyiwei on 2019-4-30 030.
 */
public interface ProductFactory {

    Phone createPhone();

    Pad createPad();

    Laptop createLaptop();

}
