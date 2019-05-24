package com.shenyiwei.dubbo.provider.api.service;

import com.shenyiwei.dubbo.provider.api.vo.User;

/**
 * Created by shenyiwei on 2019-5-23 023.
 */
public interface UserService {

    User getUser(Integer id);

    Boolean addUser(Integer id);

}
