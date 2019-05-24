package com.shenyiwei.consumer;

import com.alibaba.fastjson.JSONObject;
import com.shenyiwei.dubbo.provider.api.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by shenyiwei on 2019-5-23 023.
 */
public class ConsumerApp {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-consumer.xml");
        context.start();
        UserService userService = (UserService) context.getBean("userService");
        UserService userServiceWrite = (UserService) context.getBean("userServiceWrite");

//        User user = userService.getUser(5);
//        System.out.println(JSONObject.toJSONString(user));

        Boolean isSuccess = userServiceWrite.addUser(6);
//        Boolean isSuccess = userService.addUser(6);
        System.out.println(JSONObject.toJSONString(isSuccess));

    }

}
