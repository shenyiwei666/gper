package com.shenyiwei.designpatterns.factorys.factorymethod;


import com.shenyiwei.designpatterns.factorys.factorymethod.bean.Product;
import com.shenyiwei.designpatterns.factorys.factorymethod.factory.PhoneFactory;
import com.shenyiwei.designpatterns.factorys.factorymethod.factory.ProductFactory;

/**
 * Created by shenyiwei on 2019-4-30 030.
 */
public class Test {

    public static void main(String[] args) {
        ProductFactory factory = new PhoneFactory();
        Product product = factory.create();
        System.out.println(product.getName());
    }

}
