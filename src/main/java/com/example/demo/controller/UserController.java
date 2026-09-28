package com.example.demo.controller;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*
  UserController：接口层，负责接收浏览器请求并返回数据
  @RestController 表示这个类里的方法返回值会直接变成 JSON（不是跳转页面）
  @RequestMapping("/user") 表示这个类下所有接口都以 /user 开头
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理",description = "用户的增删改查接口")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    /*
      GET /user/list
      selectList(null) 相当于执行：SELECT id, username, password, create_time FROM user
      返回值 List<User> 会被 Spring 自动转成 JSON 数组返回给浏览器
     */
    @GetMapping("/list")
    @Operation(summary = "获取用户列表", description = "返回所有用户信息")
    public Result<List<User>> list() {
        return Result.success(userService.getUser());
    }
    @PostMapping("/add")
    @Operation(summary = "添加用户", description = "添加一个新用户")
    public Result<User> add(@Valid @RequestBody User user){
        int result = userService.addUser(user);
        return switch (result) {
            case 0 -> Result.success(user);
            case -2 -> Result.error(409, "username already exists");
            default -> Result.error("add failed");
        };
    }
    @PutMapping("/update")
    @Operation(summary = "更新用户", description = "更新指定 ID 的用户信息")
    public Result<User> update(@Valid @RequestBody User user){

        int result = userService.updateUser(user);
       return switch (result){
           case -1 -> Result.error(404,"userid unexist");
           case -2 ->Result.error(409,"username already exists");
           case 0 ->Result.success(user);
           default -> Result.error("update failed");
       };

    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除用户", description = "删除指定 ID 的用户")
    public Result<Long> delete(@PathVariable Long id){
        int result = userService.deleteUser(id);
        return switch (result){
            case -1 ->Result.error(404,"User ID does not exist");
            case 0 ->Result.success(id);
            default ->Result.error("delete failed");
        };
    }
}
