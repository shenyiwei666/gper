package com.shenyiwei.designpatterns.strategy.payments;

/**
 * Created by shenyiwei on 2019-5-8 008.
 */
public class AliPay implements Payment {
    @Override
    public void pay() {
        System.out.println("支付宝付款成功");
    }
}
