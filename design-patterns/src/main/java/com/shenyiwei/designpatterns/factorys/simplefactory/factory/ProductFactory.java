package com.shenyiwei.designpatterns.factorys.simplefactory.factory;

import com.shenyiwei.designpatterns.factorys.simplefactory.bean.Laptop;
import com.shenyiwei.designpatterns.factorys.simplefactory.bean.Pad;
import com.shenyiwei.designpatterns.factorys.simplefactory.bean.Phone;
import com.shenyiwei.designpatterns.factorys.simplefactory.bean.Product;

/**
 * Created by shenyiwei on 2019-4-30 030.
 */
public class ProductFactory {

    public Product create(Class clazz) {
//        return clazz.newInstance();
        if (clazz == Phone.class) {
            return new Phone();
        } else if (clazz == Pad.class) {
            return new Pad();
        } else if (clazz == Laptop.class) {
            return new Laptop();
        }
        return null;
    }

}
