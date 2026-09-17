package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;
import com.example.demo.mapper.UserMapper;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Override
    public List<User> getUser() {

        return userMapper.selectList(null);
    }

    @Override
    public String addUser(User user) {
        if (userMapper.selectCount(new QueryWrapper<User>().eq("username",user.getUsername())) != 0L){
            return "username already exists";
        }
        int result = userMapper.insert(user);
        if (result > 0) {
            return "add success";
        } else {
            return "add failed";
        }
    }

    @Override
    public String updateUser(User user) {
        int result = userMapper.updateById(user);
        if (result > 0) {
            return "update success";
        } else {
            return "update failed";
        }
    }

    @Override
    public String deleteUser(Long id) {
        int result = userMapper.deleteById(id);
        if (result > 0) {
            return "delete success";
        } else {
            return "delete failed";
        }
    }
}
