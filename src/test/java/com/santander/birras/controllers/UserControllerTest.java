package com.santander.birras.controllers;

import com.santander.birras.domain.User;
import com.santander.birras.services.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;

import static com.santander.birras.TestData.createUsers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    private UserController userController;

    @BeforeEach
    void setUp() {
        initMocks(this);
        userController = new UserController(userService);
    }

    @Test
    void shouldGetAllRegisteredUsers() {
        ArrayList<User> users = createUsers();

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity responseEntity = userController.getAllUsers();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userService, atLeast(1)).getAllUsers();
    }

    @Test
    void shouldGetUserById() {
        ArrayList<User> users = createUsers();

        when(userService.getUserById(1L)).thenReturn(Optional.of(createUsers().get(0)));

        ResponseEntity responseEntity = userController.getUserById(1L);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userService, atLeast(1)).getUserById(1L);
    }

    @Test
    void shouldAddUser() {
        User user = createUsers().get(0);

        ResponseEntity responseEntity = userController.addUser(user);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("User Created");
        verify(userService, atLeast(1)).addUser(user);
    }

    @Test
    void shouldUpdateUser() {
        User user = createUsers().get(0);
        user.setEmail("test@gmail.com");

        ResponseEntity responseEntity = userController.updateUser(1L, user);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("User Info Updated");
        verify(userService, atLeast(1)).updateUser(1L, user);
    }

    @Test
    void shouldDeleteUser() {
        User user = createUsers().get(0);

        ResponseEntity responseEntity = userController.deleteUser(1L);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("User Deleted");
        verify(userService, atLeast(1)).deleteUser(1L);
    }
}
