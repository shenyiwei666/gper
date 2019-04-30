package com.shenyiwei.designpatterns.factorys.simplefactory;

import com.shenyiwei.designpatterns.factorys.simplefactory.bean.Laptop;
import com.shenyiwei.designpatterns.factorys.simplefactory.bean.Product;
import com.shenyiwei.designpatterns.factorys.simplefactory.factory.ProductFactory;

/**
 * Created by shenyiwei on 2019-4-30 030.
 */
public class Test {

    public static void main(String[] args) {
        ProductFactory factory = new ProductFactory();
        Product product = factory.create(Laptop.class);
        System.out.println(product.getName());
    }

}
