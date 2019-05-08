package com.shenyiwei.designpatterns.strategy.payments;

/**
 * Created by shenyiwei on 2019-5-8 008.
 */
public class UnionPay implements Payment {
    @Override
    public void pay() {
        System.out.println("银联支付付款成功");
    }
}
