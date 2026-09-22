package com.example.demo.service;
import com.example.demo.entity.User;
import java.util.List;

public interface UserService {
    public List<User> getUser();
    public int addUser(User user);
    public int updateUser(User user);
    public int deleteUser(Long id);
}
