package com.shenyiwei.designpatterns.factory.abstractfactory;

import com.shenyiwei.designpatterns.factory.abstractfactory.bean.Laptop;
import com.shenyiwei.designpatterns.factory.abstractfactory.bean.Phone;
import com.shenyiwei.designpatterns.factory.abstractfactory.factory.LenovoProductFactory;
import com.shenyiwei.designpatterns.factory.abstractfactory.factory.ProductFactory;

/**
 * Created by shenyiwei on 2019-4-30 030.
 */
public class Test {

    public static void main(String[] args) {
        ProductFactory factory = new LenovoProductFactory();
        Phone phone = factory.createPhone();
        Laptop laptop = factory.createLaptop();
        System.out.println(phone.getName());
        System.out.println(laptop.getName());
    }

}
