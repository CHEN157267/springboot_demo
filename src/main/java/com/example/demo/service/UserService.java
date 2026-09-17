package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService {
    public List<User> getUser();
    public String addUser(User user);
    public String updateUser(User user);
    public String deleteUser(Long id);
}
