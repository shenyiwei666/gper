package com.shenyiwei.mybatis.mapper;

import com.shenyiwei.mybatis.entity.User;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by shenyiwei on 2019-5-3 003.
 */
public interface UserMapper {

    List<User> selectList();

    List<User> selectList(RowBounds rowBounds);

    User selectOne(Integer id);

    void update(User user);

}
