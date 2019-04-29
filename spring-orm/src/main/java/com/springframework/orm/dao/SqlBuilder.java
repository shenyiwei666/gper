package com.springframework.orm.dao;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by shenyiwei on 2019/4/29.
 */
public class SqlBuilder<T> {
    /**
     * 构建插入SQL语句
     * @param entity
     * @param entitySupput
     * @return
     */
    public String buildInsertSql(T entity, EntitySupput<T> entitySupput) {
        Map<String, String> fieldColumnMapping = entitySupput.getFieldColumnMapping();
        String[] columns = new String[fieldColumnMapping.size()];
        Object[] values = new Object[columns.length];
        Class[] types = new Class[columns.length];
        int length = 0;
        for (String fieldName : fieldColumnMapping.keySet()) {
            String columnName = fieldColumnMapping.get(fieldName);
            Object value = entitySupput.getFieldValue(entity, fieldName);
            if (value == null) {
                continue;
            }
            columns[length] = columnName;
            values[length] = value;
            types[length] = value.getClass();
            length++;
        }

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO `"+ entitySupput.getTableName() +"` (");
        for (int i = 0; i < length; i++) {
            sql.append("`"+ columns[i] +"`,");
            if (i == length - 1) {
                sql.setLength(sql.length() - 1);
            }
        }
        sql.append(")");

        sql.append("VALUES (");
        for (int i = 0; i < length; i++) {
            sql.append(sqlValue(values[i]));
            sql.append(",");
            if (i == length - 1) {
                sql.setLength(sql.length() - 1);
            }
        }
        sql.append(");");
        return sql.toString();
    }

    /**
     * 构建更新SQL语句
     * @param entity
     * @param entitySupput
     * @return
     */
    public String buildUpdateSql(T entity, EntitySupput<T> entitySupput) {
        Map<String, String> fieldColumnMapping = entitySupput.getFieldColumnMapping();
        String[] columns = new String[fieldColumnMapping.size()];
        Object[] values = new Object[columns.length];
        Class[] types = new Class[columns.length];
        int length = 0;
        for (String fieldName : fieldColumnMapping.keySet()) {
            String columnName = fieldColumnMapping.get(fieldName);
            Object value = entitySupput.getFieldValue(entity, fieldName);
            columns[length] = columnName;
            values[length] = value;
            types[length] = value == null ? entitySupput.getFieldType(entity, fieldName) : value.getClass();
            length++;
        }

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE `"+ entitySupput.getTableName() +"` SET");
        for (int i = 0; i < length; i++) {
            sql.append("`"+ columns[i] +"`=");
            sql.append(sqlValue(values[i]));
            sql.append(",");

            if (i == length - 1) {
                sql.setLength(sql.length() - 1);
            }
        }

        String primaryKey = entitySupput.getPrimaryKey();
        Object primaryValue = entitySupput.getFieldValue(entity, entitySupput.getPrimaryKey());
        sql.append(" WHERE " + primaryKey + "=");
        sql.append(sqlValue(primaryValue));
        sql.append(";");
        return sql.toString();
    }

    /**
     * 构建删除SQL语句
     * @param primaryKey
     * @param entitySupput
     * @return
     */
    public String buildDeleteSql(Serializable primaryKey, EntitySupput<T> entitySupput) {
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM `"+ entitySupput.getTableName() + "`");
        sql.append(" WHERE "+ entitySupput.getPrimaryKey() +"=");
        sql.append(sqlValue(primaryKey));
        sql.append(";");
        return sql.toString();
    }

    private String sqlValue(Object value) {
        if (value == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        if (value.getClass() == String.class) {
            sb.append("'");
        }
        sb.append(value);
        if (value.getClass() == String.class) {
            sb.append("'");
        }
        return sb.toString();
    }

}
