package com.shenyiwei.designpatterns.prototype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 手机生产工厂
 * Created by shenyiwei on 2019-5-1 001.
 */
public class PhoneFactory {

    /**
     * 简单创建
     * @param size
     * @return
     */
    public static List<Phone> simpleCreatePhone(int size) {
        List<Phone> phoneList = new ArrayList<>();
        List<String> macList = generateMac(size);
        Phone phone = null;
        for (String mac : macList) {
            phone = new Phone();
            phone.setBrand("Huawei");
            phone.setType("P30");
            phone.setPrice(new BigDecimal(5988.00));
            phone.setColor("white");
            phone.setSize(6.47);
            phone.setWeight(192d);
            phone.setProcessor("HUAWEI Kirin 980");
            phone.setOperatingSystem("EMUI9.1.0");
            phone.setStoreCapacity(256d);
            phone.setElectricCapacity(4200d);
            phone.setMac(mac);
            phoneList.add(phone);
        }
        return phoneList;
    }

    /**
     * 原型模式创建
     * @param size
     * @return
     */
    public static List<Phone> prototypeCreatePhone(int size) throws CloneNotSupportedException {
        List<Phone> phoneList = new ArrayList<>();
        List<String> macList = generateMac(size);

        Phone phonePrototype = new Phone();
        phonePrototype.setBrand("Huawei");
        phonePrototype.setType("P30");
        phonePrototype.setPrice(new BigDecimal(5988.00));
        phonePrototype.setColor("white");
        phonePrototype.setSize(6.47);
        phonePrototype.setWeight(192d);
        phonePrototype.setProcessor("HUAWEI Kirin 980");
        phonePrototype.setOperatingSystem("EMUI9.1.0");
        phonePrototype.setStoreCapacity(256d);
        phonePrototype.setElectricCapacity(4200d);

        Phone phone = null;
        for (String mac : macList) {
            phone = (Phone) phonePrototype.clone();
            phone.setMac(mac);
            phoneList.add(phone);
        }
        return phoneList;
    }

    private static List<String> generateMac(int size) {
        List<String> macList = new ArrayList<>();
        String uuid = null;
        for (int i = 0; i < size; i++) {
            uuid = UUID.randomUUID().toString().replace("-", "");
            macList.add(uuid);
        }
        return macList;
    }

}
