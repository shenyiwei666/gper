package com.demo;

import com.alibaba.fastjson.JSONArray;
import com.orm.demo.dao.UserDao;
import com.orm.demo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by shenyiwei on 2019/4/29.
 */
@ContextConfiguration(locations = {"classpath:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTest {
    @Autowired
    UserDao userDao;

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("Wangwu");
        user.setSex(1);
        user.setAge(18);
        userDao.insert(user);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(5);
        user.setSex(1);
        user.setAge(22);
        userDao.update(user);
    }

    @Test
    public void testDelete() {
        userDao.delete(5);
    }

    @Test
    public void testQuery() {
        List<User> list = userDao.query();
        System.out.println(JSONArray.toJSONString(list));
    }

}
