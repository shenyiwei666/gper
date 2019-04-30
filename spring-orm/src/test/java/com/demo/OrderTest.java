package com.demo;

import com.alibaba.fastjson.JSONArray;
import com.orm.demo.dao.OrderDao;
import com.orm.demo.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by shenyiwei on 2019/4/29.
 */
@ContextConfiguration(locations = {"classpath:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderTest {
    @Autowired
    OrderDao orderDao;

    @Test
    public void testInsert() {
        Order order = new Order();
        order.setUserId(1);
        order.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        order.setAmount(new BigDecimal(100));
        order.setCrateTime(new Date());
        orderDao.insert(order);
    }

    @Test
    public void testUpdate() {
        Order order = orderDao.query(1);
        order.setId(1);
        order.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        order.setAmount(new BigDecimal(500));
        orderDao.update(order);
    }

    @Test
    public void testDelete() {
        orderDao.delete(8);
    }

    @Test
    public void testQuery() {
        List<Order> list = orderDao.query();
        System.out.println(JSONArray.toJSONString(list));
    }

}
