package com.shenyiwei.designpatterns.proxys.cglibproxy;

import com.shenyiwei.designpatterns.proxys.suport.AirplaneTicket;

/**
 * Created by shenyiwei on 2019-5-7 007.
 */
public class TestCglibProxy {

    public static void main(String[] args) {
        System.out.println("--------------- 自己买票 ------------------");
        AirplaneTicket trainTicket = new AirplaneTicket();
        trainTicket.buy();

        System.out.println("\n--------------- 黄牛代理买票 ------------------");
        AirplaneTicket ticket = (AirplaneTicket) new Huangniu().getInstance(AirplaneTicket.class);
//        Ticket ticket = (TrainTicket) new Huangniu().getInstance(TrainTicket.class);
        ticket.buy();

    }

}
