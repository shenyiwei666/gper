package com.orm.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by shenyiwei on 2019/4/30.
 */
@Table(name = "t_order")
@Data
public class Order {
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "order_no")
    private String orderNo;
    private BigDecimal amount;
    @Column(name = "crate_time")
    private Date crateTime;
}
