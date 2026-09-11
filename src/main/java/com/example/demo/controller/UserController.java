package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * UserController：接口层，负责接收浏览器请求并返回数据
 *
 * @RestController 表示这个类里的方法返回值会直接变成 JSON（不是跳转页面）
 * @RequestMapping("/user") 表示这个类下所有接口都以 /user 开头
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMapper userMapper;

    /**
     * 构造方法注入：Spring 启动时自动把 UserMapper 的实现塞进来
     * （不用自己 new，这就是"依赖注入"）
     */
    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * GET /user/list
     * selectList(null) 相当于执行：SELECT id, username, password, create_time FROM user
     * 返回值 List<User> 会被 Spring 自动转成 JSON 数组返回给浏览器
     */
    @GetMapping("/list")
    public List<User> list() {
        return userMapper.selectList(null);
    }
}
