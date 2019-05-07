package com.shenyiwei.designpatterns.proxys;

import com.shenyiwei.designpatterns.proxys.suport.Ticket;
import com.shenyiwei.designpatterns.proxys.suport.TrainTicket;

/**
 * Created by shenyiwei on 2019-5-7 007.
 */
public class TestSpeed {

    public static void main(String[] args) {
        int count = 1000000;
        System.out.println("-------- create test ---------");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Ticket cglib = (TrainTicket) new com.shenyiwei.designpatterns.proxys.cglibproxy.Huangniu().getInstance(TrainTicket.class);
            cglib.buy2();
        }
        System.out.println("cglib: " + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Ticket jdk = (Ticket) new com.shenyiwei.designpatterns.proxys.jdkproxy.Huangniu().getInstance(TrainTicket.class);
            jdk.buy2();
        }
        System.out.println("jdk: " + (System.currentTimeMillis() - startTime));


        System.out.println("-------- invoke test ---------");
        startTime = System.currentTimeMillis();
        Ticket cglib = (TrainTicket) new com.shenyiwei.designpatterns.proxys.cglibproxy.Huangniu().getInstance(TrainTicket.class);
        for (int i = 0; i < count; i++) {
            cglib.buy2();
        }
        System.out.println("cglib: " + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        Ticket jdk = (Ticket) new com.shenyiwei.designpatterns.proxys.jdkproxy.Huangniu().getInstance(TrainTicket.class);
        for (int i = 0; i < count; i++) {
            jdk.buy2();
        }
        System.out.println("jdk: " + (System.currentTimeMillis() - startTime));

    }

}
