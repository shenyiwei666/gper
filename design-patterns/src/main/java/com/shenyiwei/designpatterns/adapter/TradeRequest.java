package com.shenyiwei.designpatterns.adapter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by shenyiwei on 2019-5-9 009.
 */
public class TradeRequest {
    // 银行卡号
    private String bankCardNo;
    // 身份证号码
    private String idCardNo;
    // 用户姓名
    private String realname;
    // 手机号码
    private String phone;
    // 交易金额
    private BigDecimal tradeAmt;
    // 接口版本
    private String version;
    // 交易时间
    private Date tradeTime;

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getTradeAmt() {
        return tradeAmt;
    }

    public void setTradeAmt(BigDecimal tradeAmt) {
        this.tradeAmt = tradeAmt;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }
}
