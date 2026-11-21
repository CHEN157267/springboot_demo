package com.example.demo.agent;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserTools {
    private static final Logger logger = LoggerFactory.getLogger(UserTools.class);
    private final UserService userService;
    public UserTools(UserService userService) {
        this.userService = userService;
    }

    //@Tool 的 description —— 是写给模型看的，不是写给人看的注释
    @Tool(description = "查询所有用户列表，返回用户名和创建时间。当用户问'有哪些用户''一共几个人''所有人的信息'时使用。不返回密码。")
    public List<User> getUser() {
        long startTime = System.currentTimeMillis();
        List<User> users = userService.getUser();
        long endTime = System.currentTimeMillis();
        logger.info("[工具] getUser 返回 {} 条，耗时 {} ms", users.size(), endTime - startTime);
        return users;
    }
    @Tool(description = "根据用户 ID 查询单个用户，返回 id、用户名和创建时间。当用户问‘id 为 X 的用户’‘第 X 号’时使用。查不到时返回空。")
    public User getUserById(@ToolParam(description = "用户id")Long id){
        long startTime = System.currentTimeMillis();
        User user =  userService.getUserById(id);
        long endTime = System.currentTimeMillis();
        //[工具] getUserById id=999 → 未找到，耗时 3ms
        //[工具] getUserById id=4   → 找到 zhangsan，耗时 2ms
        if (user != null)
            logger.info("[工具] getUserById id =  {} -> 找到 {} 耗时 {} ms", id, user.getUsername(),endTime - startTime);
        else
            logger.info("[工具] getUserById id = {} -> 未找到  耗时 {} ms", id,endTime - startTime);
        return user;
    }






}
