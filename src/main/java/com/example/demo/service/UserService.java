package com.example.demo.service;
import com.example.demo.entity.User;
import java.util.List;

public interface UserService {
     List<User> getUser();
     void addUser(User user);
     void updateUser(User user);
     void deleteUser(Long id);
}
