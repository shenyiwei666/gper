package com.shenyiwei.designpatterns.strategy;

import com.shenyiwei.designpatterns.strategy.payments.Payment;

/**
 * Created by shenyiwei on 2019-5-8 008.
 */
public class Test {

    public static void main(String[] args) {
        String paymentType = Payment.ALIPAY;
        PaymentStrategy.get(paymentType).pay();
    }

}
