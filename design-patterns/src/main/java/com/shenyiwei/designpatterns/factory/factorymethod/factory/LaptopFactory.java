package com.shenyiwei.designpatterns.factory.factorymethod.factory;


import com.shenyiwei.designpatterns.factory.factorymethod.bean.Laptop;

/**
 * Created by shenyiwei on 2019-4-30 030.
 */
public class LaptopFactory implements ProductFactory {
    @Override
    public Laptop create() {
        return new Laptop();
    }
}
