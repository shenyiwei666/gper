package com.shenyiwei.dubbo.provider.api.service.mock;

import com.shenyiwei.dubbo.provider.api.service.UserService;
import com.shenyiwei.dubbo.provider.api.vo.User;

/**
 * Created by shenyiwei on 2019-5-24 024.
 */
public class UserServiceMock implements UserService {
    @Override
    public User getUser(Integer id) {
        System.out.println("getUser timeout");
        return null;
    }

    @Override
    public Boolean addUser(Integer id) {
        System.out.println("addUser timeout");
        return false;
    }
}
