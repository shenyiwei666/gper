package com.shenyiwei.designpatterns.strategy.payments;

/**
 * Created by shenyiwei on 2019-5-8 008.
 */
public class WechatPay implements Payment {
    @Override
    public void pay() {
        System.out.println("微信支付付款成功");
    }
}
