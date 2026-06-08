package com.example.users.service;

import com.example.users.dao.UserDao;
import com.example.users.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUser(String name) {
        User user = new User(name);
        return userDao.save(user);
    }

    public User getUser(Long id) {
        return userDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found : " + id));
    }

    public void deleteUser(Long id) {
        userDao.delete(id);
    }

    public boolean isValid(Long id) {
        return userDao.existsById(id);
    }
}
