package com.santander.birras.services;

import com.santander.birras.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    void addUser(User user);
    void updateUser(Long id, User user);
    void deleteUser(Long id);
    Optional<User> findUserByUsername(String userName);
}
