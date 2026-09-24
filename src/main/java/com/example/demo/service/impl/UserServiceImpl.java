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
    public int addUser(User user) {
        int result;
        if (userMapper.selectCount(new QueryWrapper<User>().eq("username",user.getUsername())) != 0L){
            return -2;
        }
        result = userMapper.insert(user);
        if (result > 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public int updateUser(User user) {
        if (!(userMapper.exists(new QueryWrapper<User>().eq("id", user.getId()))))
            return -1;
        if (userMapper.selectCount(new QueryWrapper<User>().eq("username", user.getUsername()).ne("id", user.getId())) != 0L)
            return -2;
        if (userMapper.updateById(user) > 0)
            return 0;
        return 1;

    }

    @Override
    public int deleteUser(Long id) {
       int result = userMapper.deleteById(id);
       if (result > 0)
           return 0;
      return -1;
    }
}
