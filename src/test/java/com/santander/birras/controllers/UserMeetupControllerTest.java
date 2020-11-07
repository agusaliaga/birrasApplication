package com.santander.birras.controllers;

import com.santander.birras.domain.UserMeetup;
import com.santander.birras.services.impl.UserMeetupServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.santander.birras.TestData.createMeetup;
import static com.santander.birras.TestData.createUsers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserMeetupControllerTest {

    @Mock
    private UserMeetupServiceImpl userMeetupService;

    private UserMeetupController userMeetupController;

    @BeforeEach
    void setUp() {
        initMocks(this);
        userMeetupController = new UserMeetupController(userMeetupService);
    }

    @Test
    void shouldAddUserToMeetup() {
        UserMeetup userMeetups = createUserMeetup();

        ResponseEntity responseEntity = userMeetupController.addUserToMeetup(userMeetups);

        assertThat(responseEntity.getBody()).isEqualTo("User added to meetup");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userMeetupService, atLeast(1)).addUserToMeetup(userMeetups);
    }

    @Test
    void shouldShowAllRegistrations() {
        UserMeetup userMeetups = createUserMeetup();

        ResponseEntity responseEntity = userMeetupController.showAllMeetupsRegistrations();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userMeetupService, atLeast(1)).showAllMeetupsRegistrations();
    }

    @Test
    void shouldCheckInUserIntoMeetup() {
        UserMeetup userMeetups = createUserMeetup();

        ResponseEntity responseEntity = userMeetupController.checkInMeetup(userMeetups);

        assertThat(responseEntity.getBody()).isEqualTo("User checked in");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userMeetupService, atLeast(1)).checkInUser(userMeetups);
    }

    @Test
    void shouldRegisterUserAttendanceToMeetup() {
        UserMeetup userMeetups = createUserMeetup();

        ResponseEntity responseEntity = userMeetupController.attendedMeetup(userMeetups);

        assertThat(responseEntity.getBody()).isEqualTo("User attendance recorded");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userMeetupService, atLeast(1)).userAttended(userMeetups);
    }

    private UserMeetup createUserMeetup() {
        return UserMeetup.builder().meetup(createMeetup()).user(createUsers().get(0)).build();
    }
}
