package com.example.demo.service;
import com.example.demo.entity.User;
import java.util.List;

public interface UserService {
     List<User> getUser();
     int addUser(User user);
     int updateUser(User user);
     int deleteUser(Long id);
}
