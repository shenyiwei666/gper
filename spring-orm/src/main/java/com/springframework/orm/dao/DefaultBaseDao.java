package com.springframework.orm.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by shenyiwei on 2019/4/29.
 */
@Slf4j
public class DefaultBaseDao<T> implements BaseDao<T> {
    JdbcTemplate jdbcWriter = null;
    JdbcTemplate jdbcReader = null;
    EntitySupput<T> entitySupput = new EntitySupput(getEntityClass());
    SqlBuilder<T> sqlBuilder = new SqlBuilder<T>();

    @Resource(name = "dataSource")
    protected void setDataSource(DataSource dataSource) {
        jdbcWriter = new JdbcTemplate(dataSource);
        jdbcReader = new JdbcTemplate(dataSource);
    }

    @Override
    public Long insert(T entity) {
        String sql = sqlBuilder.buildInsertSql(entity, entitySupput);
        log.debug(sql);
        jdbcWriter.execute(sql);
        return null;
    }

    @Override
    public boolean delete(Serializable primaryKey) {
        String sql = sqlBuilder.buildDeleteSql(primaryKey, entitySupput);
        log.debug(sql);
        jdbcWriter.execute(sql);
        return true;
    }

    @Override
    public boolean update(T entity) {
        String sql = sqlBuilder.buildUpdateSql(entity, entitySupput);
        log.debug(sql);
        jdbcWriter.execute(sql);
        return true;
    }

    @Override
    public List<T> query() {
        String sql = sqlBuilder.buildQuerySql(entitySupput);
        log.debug(sql);
        return jdbcReader.query(sql, createRowMapper());
    }

    @Override
    public T query(Serializable primaryKey) {
        String sql = sqlBuilder.buildQueryForPrimaryKeySql(primaryKey, entitySupput);
        log.debug(sql);
        return (T) jdbcReader.queryForObject(sql, createRowMapper());
    }

    @Override
    public List<T> queryForSql(String sql) {
        log.debug(sql);
        return jdbcReader.query(sql, createRowMapper());
    }

    private RowMapper createRowMapper() {
        return new RowMapper<T>() {
            @Override
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                try {
                    T entity = entitySupput.getEntityClass().newInstance();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        Object value = rs.getObject(i);
                        String columnName = metaData.getColumnName(i);
                        entitySupput.setFieldValueByColumn(entity, columnName, value);
                    }
                    return entity;
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    private Class getEntityClass() {
        Type genType = getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return null;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (params.length == 0) {
            return null;
        }
        return (Class) params[0];
    }

}
