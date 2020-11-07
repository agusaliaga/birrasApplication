package com.santander.birras.services;

import com.santander.birras.constants.EmailConstants;
import com.santander.birras.domain.User;
import com.santander.birras.repositories.UserRepository;
import com.santander.birras.services.impl.EmailServiceImpl;
import com.santander.birras.services.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.santander.birras.TestData.createUsers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailServiceImpl emailService;

    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        userService = new UserServiceImpl(userRepository, emailService);
    }

    @Test
    void shouldGetAllUsers() {
        List<User> users = createUsers();

        when(userRepository.findAll()).thenReturn(users);

        List<User> returnedUsers = userService.getAllUsers();

        verify(userRepository, atLeast(1)).findAll();
        assertThat(returnedUsers).isEqualTo(users);
    }

    @Test
    void shouldGetUserById() {
        User user = createUsers().get(0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> returnedUser = userService.getUserById(1L);

        verify(userRepository, atLeast(1)).findById(1L);
        assertThat(returnedUser).isEqualTo(Optional.of(user));
    }

    @Test
    void shouldAddUser() {
        User user = createUsers().get(0);

        userService.addUser(user);

        verify(userRepository, atLeast(1)).save(user);
        verify(emailService, atLeast(1)).sendSimpleMessage(user.getEmail(),
                                                           EmailConstants.USER_CREATED,
                                                           EmailConstants.USER_CREATED_TEXT);
    }

    @Test
    void shouldUpdateUser() {
        User user = createUsers().get(0);

        userService.updateUser(1L, user);

        verify(userRepository, atLeast(1)).save(user);
    }

    @Test
    void shouldDeleteUser() {
        User user = createUsers().get(0);

        userService.deleteUser(1L);

        verify(userRepository, atLeast(1)).deleteById(user.getId());
    }

    @Test
    void shouldFindUserByUsername() {
        User user = createUsers().get(0);

        when(userRepository.findUserByUserName("agustina")).thenReturn(Optional.of(user));

        Optional<User> returnedUser = userService.findUserByUsername(user.getUserName());

        verify(userRepository, atLeast(1)).findUserByUserName("agustina");
        assertThat(returnedUser).isEqualTo(Optional.of(user));
    }
}
