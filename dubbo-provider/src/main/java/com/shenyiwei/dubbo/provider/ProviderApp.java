package com.shenyiwei.dubbo.provider;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;
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

    private void test() {
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class)
                .getAdaptiveExtension();
    }

}
