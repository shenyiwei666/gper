package com.shenyiwei.dubbo.provider.service;

import com.shenyiwei.dubbo.provider.api.service.UserService;
import com.shenyiwei.dubbo.provider.api.vo.User;

/**
 * Created by shenyiwei on 2019-5-23 023.
 */
public class UserServicceImpl implements UserService {

    @Override
    public User getUser(Integer id) {
        System.out.println("getUser id = " + id);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new User(id, "name" + id, 1, 22);
    }

    @Override
    public Boolean addUser(Integer id) {
        System.out.println("addUser id = " + id);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return id / 2 == 0;
    }
}
