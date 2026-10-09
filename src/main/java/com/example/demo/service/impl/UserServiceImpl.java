package com.example.demo.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.User;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.mapper.UserMapper;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    // 密码加密器（它无状态、线程安全，一个实例全局够用；所以不用放进构造器。）
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final UserMapper userMapper;
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Override
    public List<User> getUser() {
        return userMapper.selectList(null);
    }

    @Override
    public void addUser(User user) {
        if (userMapper.selectCount(new QueryWrapper<User>().eq("username",user.getUsername())) != 0L){
            throw new BusinessException("Username already exists", 409);
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        if (userMapper.insert(user) > 0) {
            return;
        }
        throw new IllegalStateException("Failed to add user");
    }

    @Override
    public void updateUser(User user) {
        if (!(userMapper.exists(new QueryWrapper<User>().eq("id", user.getId()))))
            throw new BusinessException("User not found", 404);
        if (userMapper.selectCount(new QueryWrapper<User>().eq("username", user.getUsername()).ne("id", user.getId())) != 0L)
            throw new BusinessException("Username already exists", 409);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (userMapper.updateById(user) != 0)
            return ;
//0 不是"值没变" ——〔实测〕useAffectedRows=false 时返回的是"匹配行数"，值完全没变也返回 1。
//0 也不是"系统出错"  —— 系统问题（连不上库/超时）会抛异常，不会返回 0。
//所以 0 ⟺ id 不存在 → 404。这个答案成立。
        //末尾这步防的是并发删除，正常流程走不到
        throw new BusinessException("User not found", 404);

    }

    @Override
    public void deleteUser(Long id) {
       int result = userMapper.deleteById(id);
       if (result > 0)
           return ;
      throw new BusinessException("User not found", 404);
    }
}
