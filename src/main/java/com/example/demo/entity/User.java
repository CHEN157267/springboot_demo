package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 用户实体类：一张 Java 类 ↔ 数据库里的一张表
 * 类名 User   -> 表名 user
 * 字段名      -> 列名
 */
@TableName("user")
public class User {

    /** 主键，对应数据库的 id 列；IdType.AUTO 表示交给数据库自增 */
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    /** 对应 create_time 列（下划线转驼峰在配置文件里开了） */
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
