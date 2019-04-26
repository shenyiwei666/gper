package com.demo.controller;

import com.demo.service.UserService;
import com.springframework.stereotype.Autowired;
import com.springframework.stereotype.Controller;
import com.springframework.stereotype.RequestMapping;
import com.springframework.stereotype.RequestParam;
import com.springframework.webmvc.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping("/error")
    public ModelAndView error() {
        try {
            userService.error();
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> model = new HashMap<>();
            model.put("message", e.getMessage());
            model.put("stackTrace", Arrays.toString(e.getStackTrace()));
            return new ModelAndView("/500", model);
        }
        return null;
    }



}
