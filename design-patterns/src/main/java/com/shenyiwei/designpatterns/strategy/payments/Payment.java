package com.shenyiwei.designpatterns.strategy.payments;

/**
 * Created by shenyiwei on 2019-5-8 008.
 */
public interface Payment {
    /**
     * 支付宝支付
     */
    String ALIPAY = "alipay";
    /**
     * 微信支付
     */
    String WECHATPAY = "wechatpay";
    /**
     * 银联支付
     */
    String UNIONPAY = "unionpay";
    /**
     * 京东支付
     */
    String JDPAY = "jdpay";


    /**
     * 支付
     */
    void pay();

}
