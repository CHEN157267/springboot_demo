package com.example.demo.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.User;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.mapper.UserMapper;

import java.time.Duration;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    // 密码加密器（它无状态、线程安全，一个实例全局够用；所以不用放进构造器。）
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final UserMapper userMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;
    public UserServiceImpl(UserMapper userMapper, StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
        this.userMapper = userMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }
    @Override
    public List<User> getUser() {
        try {
            String json = stringRedisTemplate.opsForValue().get("user:list");
            if (json != null) {
                log.info("缓存命中：从 Redis 读取用户列表");
                return objectMapper.readValue(json, new TypeReference<List<User>>() {
                });
            }

            List<User> result = userMapper.selectList(null);
            stringRedisTemplate.opsForValue().set("user:list",objectMapper.writeValueAsString (result),Duration.ofSeconds(30));
            log.info("缓存未命中：回源查库并写入缓存");
            return result;
        }
            catch (JsonProcessingException e) {
                evictCache("user:list");
                log.warn("缓存数据损坏，已清除，{}", e.getMessage());
                return userMapper.selectList(null);
        }
            catch (DataAccessException e) {
                log.warn("Redis 不可用，直接回源，{}", e.getMessage());
                return userMapper.selectList(null);
        }
    }

    @Override
    public void addUser(User user) {
        if (userMapper.selectCount(new QueryWrapper<User>().eq("username",user.getUsername())) != 0L){
            throw new BusinessException("Username already exists", 409);
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        if (userMapper.insert(user) > 0) {
            evictCache("user:list");
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
        if (userMapper.updateById(user) != 0) {
            evictCache("user:list");
            return;
        }
//0 不是"值没变" ——〔实测〕useAffectedRows=false 时返回的是"匹配行数"，值完全没变也返回 1。
//0 也不是"系统出错"  —— 系统问题（连不上库/超时）会抛异常，不会返回 0。
//所以 0 ⟺ id 不存在 → 404。这个答案成立。
        //末尾这步防的是并发删除，正常流程走不到
        throw new BusinessException("User not found", 404);

    }

    @Override
    public void deleteUser(Long id) {
       if (userMapper.deleteById(id) > 0) {
           evictCache("user:list");
           return;
       }
      throw new BusinessException("User not found", 404);
    }

    private void evictCache(String key) {
        try {
            stringRedisTemplate.delete(key);
        } catch (DataAccessException e) {
            log.warn("Redis旧缓存删除失败，{}", e.getMessage());
        }
    }
}
