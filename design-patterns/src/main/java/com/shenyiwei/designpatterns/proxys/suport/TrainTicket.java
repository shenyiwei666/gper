package com.shenyiwei.designpatterns.proxys.suport;

/**
 * 火车票
 * Created by shenyiwei on 2019-5-7 007.
 */
public class TrainTicket implements Ticket {

    @Override
    public void buy() {
        if (WhiteVerify.isWhite()) {
            System.out.println("出票成功（内部票）");
        } else {
            if (System.currentTimeMillis() % 3 == 0) {
                System.out.println("出票成功");
            } else {
                System.out.println("出票失败：余票不足");
            }
        }
    }

    @Override
    public void buy2() {
        if (WhiteVerify.isWhite()) {
        } else {
            if (System.currentTimeMillis() % 3 == 0) {
            } else {
            }
        }
    }

}
