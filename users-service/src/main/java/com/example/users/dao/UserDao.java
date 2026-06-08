package com.example.users.dao;

import com.example.users.entity.User;

import java.util.Optional;

public interface UserDao {

    User save(User user);

    Optional<User> findById(Long id);

    void delete(Long id);

    boolean existsById(Long id);
}
