package com.demo.service.impl;

import com.demo.service.UserService;
import com.springframework.stereotype.Service;

/**
 * Created by shenyiwei on 2019/4/16.
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String add(String name, Integer age) {
        return "Add success, name=" + name + " age=" + age;
    }

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }

    @Override
    public void error() {
        String s = null;
        s.toString();
    }
}
