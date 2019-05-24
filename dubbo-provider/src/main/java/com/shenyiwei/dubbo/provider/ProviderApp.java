package com.shenyiwei.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by shenyiwei on 2019-5-23 023.
 */
public class ProviderApp {

    public static void main(String[] args) throws IOException {
//        Main.main(new String[] {"dubbo-provider.xml"});

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-provider.xml");
        context.start();
        System.out.println(">>>>>>>>>>>>>> start success");
        System.in.read();
    }

}
