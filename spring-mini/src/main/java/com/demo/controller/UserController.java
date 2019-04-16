package com.demo.controller;

import com.demo.service.UserService;
import com.springframework.stereotype.Autowired;
import com.springframework.stereotype.Controller;
import com.springframework.stereotype.RequestMapping;
import com.springframework.stereotype.RequestParam;

/**
 * Created by shenyiwei on 2019/4/16.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;

    @RequestMapping("/add")
    public String add(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        return userService.add(name, age);
    }

    @RequestMapping("/hello")
    public String hello(@RequestParam("name") String name) {
        return userService.hello(name);
    }

}
