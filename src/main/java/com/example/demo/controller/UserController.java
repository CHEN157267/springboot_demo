package com.example.demo.controller;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*
  UserController：接口层，负责接收浏览器请求并返回数据

  @RestController 表示这个类里的方法返回值会直接变成 JSON（不是跳转页面）
  @RequestMapping("/user") 表示这个类下所有接口都以 /user 开头
 */
@RestController
@RequestMapping("/user")
public class UserController {

//    private final UserMapper userMapper;

    /*
      构造方法注入：Spring 启动时自动把 UserMapper 的实现塞进来
      （不用自己 new，这就是"依赖注入"）
     */
//    public UserController(UserMapper userMapper) {
//
//        this.userMapper = userMapper;
//    }

    /*
      GET /user/list
      selectList(null) 相当于执行：SELECT id, username, password, create_time FROM user
      返回值 List<User> 会被 Spring 自动转成 JSON 数组返回给浏览器
     */
    @Autowired private UserService userService;
    @GetMapping("/list")
    public List<User> list() {
        return userService.getUser();
    }
    @PostMapping("/add")
    public String add(@RequestBody User user){

        return userService.addUser(user);
    }
    @PutMapping("/update")
    public String update(@RequestBody User user){
        return userService.updateUser(user);
    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        return userService.deleteUser(id);
    }



//    @GetMapping("/list")
//    public List<User> list() {
//        return userMapper.selectList(null);
//    }
//    @PostMapping("/add")
//    public String add(@RequestBody User user){
//        userMapper.insert(user);
//        return "add success";
//    }
//    @PutMapping("/update")
//    public String update(@RequestBody User user){
//        int result = userMapper.updateById(user);
//        if (result > 0){
//            return "update success";
//        }else {
//            return "update failed";
//        }
//    }
//    @DeleteMapping("/delete/{id}")
//    public String delete(@PathVariable Long id){
//        if (userMapper.deleteById(id) > 0) {
//            return "delete success";
//        } else {
//            return "delete failed";
//        }
//    }
//    @DeleteMapping("/delete")
//    public String delete(@RequestParam String id){
//        int result = userMapper.deleteById(id);
//        return "delete success, delete id =" + result;
//    }

}
