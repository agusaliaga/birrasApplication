package com.santander.birras.services.impl;

import com.santander.birras.constants.EmailConstants;
import com.santander.birras.domain.User;
import com.santander.birras.repositories.UserRepository;
import com.santander.birras.services.UserService;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final static String salt = "$2a$04$IwmFubEGW8Nw.majttflsO";

    private final UserRepository userRepository;
    private final EmailServiceImpl emailService;


    public UserServiceImpl(UserRepository userRepository,
                           EmailServiceImpl emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void addUser(User user) {
        userRepository.save(user);

        emailService.sendSimpleMessage(user.getEmail(), EmailConstants.USER_CREATED, EmailConstants.USER_CREATED_TEXT);
    }

    public void updateUser(Long id, User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findUserByUsername(String userName) {
        return userRepository.findUserByUserName(userName);
    }


    public Optional<User> getUser(String username, String password) throws Exception {

        Optional<User> user = userRepository.findUserByUserName(username);

        if(user == null) {
            throw new Exception("User does not exist: " + username );
        }

        String passwordEncoded = encode(password);

        if(!user.get().getPassword().equals(passwordEncoded)) {
            throw new Exception("Wrong password.");
        }
        return user;
    }

    public String encode(CharSequence rawPassword) {
        return BCrypt.hashpw(rawPassword.toString(), salt);
    }

}
