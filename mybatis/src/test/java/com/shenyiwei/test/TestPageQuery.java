package com.shenyiwei.test;

import com.alibaba.fastjson.JSONObject;
import com.shenyiwei.mybatis.entity.User;
import com.shenyiwei.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * Created by shenyiwei on 2019-5-3 003.
 */
public class TestPageQuery {

    public static void main(String[] args) {
        SqlSession sqlSession = null;
        try {
            String resource = "mybatis-config.xml";
            InputStream resourceAsStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
            sqlSession = sqlFactory.openSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

//            User user = mapper.selectOne(1);
//            List<User> list = mapper.selectList(new RowBounds(4, 4));
            List<User> list = mapper.selectList();
            System.out.println("\n\n" + JSONObject.toJSONString(list));

            User user = mapper.selectOne(1);
            System.out.println("\n\n" + JSONObject.toJSONString(user));


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }


}
