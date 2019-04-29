package com.springframework.orm.dao;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenyiwei on 2019/4/29.
 */
public class EntitySupput<T> {
    private Class<T> entityClass;
    private String tableName;
    private String primaryKey;
    private Map<String, Field> columnFieldMapping = new HashMap<>();
    private Map<String, String> fieldColumnMapping = new HashMap<>();

    public EntitySupput(Class entityClass) {
        this.entityClass = entityClass;
        init();
    }

    private void init() {
        initTableName();
        initPrimaryKey();
        initFieldColumnMapping();
    }

    /**
     * 根据实体类的注解获取表名
     * @return
     */
    private void initTableName() {
        if (entityClass == null) {
            return;
        }
        if (!entityClass.isAnnotationPresent(Table.class)) {
            return;
        }
        Table table = (Table) entityClass.getAnnotation(Table.class);
        tableName = table.name();
    }

    /**
     * 获取表中的主键名
     * @return
     */
    private void initPrimaryKey() {
        if (entityClass == null) {
            return;
        }
        String assignPrimaryKey = null;
        String guessPrimaryKey = null;
        for (Field field : entityClass.getDeclaredFields()) {
            String fieldName = field.getName();
            if (fieldName.equals("id")) {
                guessPrimaryKey = fieldName;
            }
            if (field.isAnnotationPresent(Id.class)) {
                assignPrimaryKey = fieldName;
            }
        }
        if (assignPrimaryKey != null) {
            primaryKey = assignPrimaryKey;
            return;
        }
        if (guessPrimaryKey != null) {
            primaryKey = guessPrimaryKey;
            return;
        }
    }

    /**
     * 根据实体类获取属性名与数据库列名的映射关系
     * @return
     */
    private void initFieldColumnMapping() {
        if (entityClass == null) {
            return;
        }
        for (Field field : entityClass.getDeclaredFields()) {
            String fieldName = field.getName();
            String columnName = fieldName;
            if (field.isAnnotationPresent(Column.class)) {
                columnName = field.getAnnotation(Column.class).name();
            }
            fieldColumnMapping.put(fieldName, columnName);
            columnFieldMapping.put(columnName, field);
        }
    }

    public Object getFieldValue(T entity, String fieldName) {
        Field field = getField(entity, fieldName);
        if (field == null) {
            return null;
        }
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class getFieldType(T entity, String fieldName) {
        Field field = getField(entity, fieldName);
        if (field == null) {
            return null;
        }
        return field.getType();
    }

    public Field getField(T entity, String fieldName) {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }

    public void setFieldValueByColumn(T entity, String columnName, Object value) {
        Field field = columnFieldMapping.get(columnName);
        if (entity == null || field == null) {
            return;
        }
        try {
            field.setAccessible(true);
            field.set(entity, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public String getTableName() {
        return tableName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public Map<String, Field> getColumnFieldMapping() {
        return columnFieldMapping;
    }

    public Map<String, String> getFieldColumnMapping() {
        return fieldColumnMapping;
    }
}
