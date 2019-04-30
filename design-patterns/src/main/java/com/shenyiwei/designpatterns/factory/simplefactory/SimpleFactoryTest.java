package com.shenyiwei.designpatterns.factory.simplefactory;

import com.shenyiwei.designpatterns.factory.Laptop;
import com.shenyiwei.designpatterns.factory.Pad;
import com.shenyiwei.designpatterns.factory.Phone;

/**
 * Created by shenyiwei on 2019-4-30 030.
 */
public class SimpleFactoryTest {

    public static void main(String[] args) {
        Phone phone = (Phone) SimpleFactory.create(Phone.class);
        System.out.println(phone.getName());

        Pad pad = (Pad) SimpleFactory.create(Pad.class);
        System.out.println(pad.getName());

        Laptop laptop = (Laptop) SimpleFactory.create(Laptop.class);
        System.out.println(laptop.getName());
    }

}
