package com.shenyiwei.designpatterns.adapter;

/**
 * Created by shenyiwei on 2019-5-9 009.
 */
public class TradeService {

    /**
     * 交易请求处理
     * @param request
     */
    public void trade(TradeRequest request) {
        // 如果是1.0老版本则使用适配器做兼容处理
        if ("1.0".equals(request.getVersion())) {
            request = new OldVersionAdapter().process(request);
        }
        sendChannel(request);
    }

    /**
     * 发送渠道完成交易
     * @param request
     */
    private void sendChannel(TradeRequest request) {
        // 渠道tradeTime字段必填
    }

}
