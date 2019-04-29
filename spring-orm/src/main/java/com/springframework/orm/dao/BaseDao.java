package com.springframework.orm.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shenyiwei on 2019/4/29.
 */
public interface BaseDao<T> {
    Long insert(T entity);

    boolean delete(Serializable id);

    boolean update(T entity);

    List<T> query();

}
