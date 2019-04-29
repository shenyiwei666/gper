package com.orm.demo.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by shenyiwei on 2019/4/29.
 */
@Table(name = "user")
@Data
public class User {
    @Id
    private Integer id;
    private String name;
    private Integer sex;
    private Integer age;
}
