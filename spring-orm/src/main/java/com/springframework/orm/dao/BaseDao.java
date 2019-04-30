package com.springframework.orm.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shenyiwei on 2019/4/29.
 */
public interface BaseDao<T> {
    /**
     * 插入
     * @param entity
     * @return
     */
    Long insert(T entity);

    /**
     * 删除
     * @param primaryKey    主键
     * @return
     */
    boolean delete(Serializable primaryKey);

    /**
     * 更新
     * @param entity
     * @return
     */
    boolean update(T entity);

    /**
     * 查询所有
     * @return
     */
    List<T> query();

    /**
     * 根据主键查询
     * @param primaryKey
     * @return
     */
    T query(Serializable primaryKey);

    /**
     * 根据sql查询
     * @param sql
     * @return
     */
    List<T> queryForSql(String sql);

}
