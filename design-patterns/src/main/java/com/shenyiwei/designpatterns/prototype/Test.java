package com.shenyiwei.designpatterns.prototype;

import java.util.List;

/**
 * Created by shenyiwei on 2019-5-1 001.
 */
public class Test {

    public static void main(String[] args) throws CloneNotSupportedException {
        int createSize = 10;
        long start1 = System.currentTimeMillis();
        List<Phone> phoneList1 = PhoneFactory.simpleCreatePhone(createSize);
        System.out.println("简单模式创建耗时：" + (System.currentTimeMillis() - start1));

//        for (Phone phone : phoneList1) {
//            System.out.println(phone + " : " + JSONObject.toJSONString(phone));
//        }

        long start2 = System.currentTimeMillis();
        List<Phone> phoneList2 = PhoneFactory.prototypeCreatePhone(createSize);
        System.out.println("原型模式创建耗时：" + (System.currentTimeMillis() - start2));

//        System.out.println("\n\n");
//        for (Phone phone : phoneList2) {
//            System.out.println(phone + " : " + JSONObject.toJSONString(phone));
//        }
    }

}
