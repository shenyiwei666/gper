package com.shenyiwei.designpatterns.adapter;

import java.util.Date;

/**
 * Created by shenyiwei on 2019-5-9 009.
 */
public class OldVersionAdapter {

    public TradeRequest process(TradeRequest request) {
        if (request.getTradeTime() == null) {
            request.setTradeTime(new Date());
        }
        return request;
    }

}
