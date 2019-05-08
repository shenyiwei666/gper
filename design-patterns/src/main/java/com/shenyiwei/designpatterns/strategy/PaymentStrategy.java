package com.shenyiwei.designpatterns.strategy;

import com.shenyiwei.designpatterns.strategy.payments.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenyiwei on 2019-5-8 008.
 */
public class PaymentStrategy {

    private static Map<String, Payment> payments = new HashMap<>();

    static {
        payments.put(Payment.ALIPAY, new AliPay());
        payments.put(Payment.WECHATPAY, new WechatPay());
        payments.put(Payment.UNIONPAY, new UnionPay());
        payments.put(Payment.JDPAY, new JdPay());
    }

    public static Payment get(String paymentType) {
        return payments.get(paymentType);
    }

}
