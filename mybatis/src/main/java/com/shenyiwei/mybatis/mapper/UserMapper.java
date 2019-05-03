package com.shenyiwei.mybatis.mapper;

import com.shenyiwei.mybatis.entity.User;

import java.util.List;

/**
 * Created by shenyiwei on 2019-5-3 003.
 */
public interface UserMapper {

    List<User> selectList();

    User selectOne(Integer id);

    void update(User user);

}
