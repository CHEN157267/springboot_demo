package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * 用户实体类：一张 Java 类 ↔ 数据库里的一张表
 * 类名 User   -> 表名 user
 * 字段名      -> 列名
 */
@TableName("user")
@Schema(description = "用户实体类")
public class User {

    /** 主键，对应数据库的 id 列；IdType.AUTO 表示交给数据库自增 */
    @TableId(type = IdType.AUTO)
    @Schema(description = "用户ID")
    private Long id;
    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "[_a-zA-Z0-9]{6,20}",message = "密码格式不正确")
    @JsonProperty(access = WRITE_ONLY)
    @Schema(description = "密码")
    private String password;

    /** 对应 create_time 列（下划线转驼峰在配置文件里开了） */
    @Schema(description = "创建时间")
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
