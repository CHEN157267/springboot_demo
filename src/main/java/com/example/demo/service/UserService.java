package com.example.demo.service;
import com.example.demo.entity.User;
import java.util.List;

public interface UserService {
    public List<User> getUser();
    public String addUser(User user);
    public String updateUser(User user);
    public String deleteUser(Long id);
}
