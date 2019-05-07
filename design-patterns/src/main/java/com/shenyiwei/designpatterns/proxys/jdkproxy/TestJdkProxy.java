package com.shenyiwei.designpatterns.proxys.jdkproxy;

import com.shenyiwei.designpatterns.proxys.suport.Ticket;
import com.shenyiwei.designpatterns.proxys.suport.TrainTicket;

/**
 * Created by shenyiwei on 2019-5-7 007.
 */
public class TestJdkProxy {

    public static void main(String[] args) {
        System.out.println("--------------- 自己买票 ------------------");
        TrainTicket trainTicket = new TrainTicket();
        trainTicket.buy();

        System.out.println("\n--------------- 黄牛代理买票 ------------------");
        Ticket ticket = (Ticket) new Huangniu().getInstance(TrainTicket.class);
//        AirplaneTicket ticket = (AirplaneTicket) new Huangniu().getInstance(AirplaneTicket.class);
        ticket.buy();

    }

}
