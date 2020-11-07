package com.santander.birras.services;

import com.santander.birras.constants.EmailConstants;
import com.santander.birras.domain.Meetup;
import com.santander.birras.domain.User;
import com.santander.birras.domain.UserMeetup;
import com.santander.birras.repositories.UserMeetupRepository;
import com.santander.birras.services.impl.EmailServiceImpl;
import com.santander.birras.services.impl.UserMeetupServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static com.santander.birras.TestData.createMeetup;
import static com.santander.birras.TestData.createUserMeetup;
import static com.santander.birras.TestData.createUsers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserMeetupServiceImplTest {

    @Mock
    private UserMeetupRepository userMeetupRepository;
    @Mock
    private EmailServiceImpl emailService;

    UserMeetupServiceImpl userMeetupService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        userMeetupService = new UserMeetupServiceImpl(userMeetupRepository, emailService);
    }

    @Test
    void shouldAddUserToMeetup() {
        User userOne = createUsers().get(0);
        Meetup meetup = createMeetup();

        UserMeetup userMeetupOne = UserMeetup.builder().meetup(meetup).user(userOne).build();

        userMeetupService.addUserToMeetup(userMeetupOne);

        verify(userMeetupRepository, atLeast(1)).save(userMeetupOne);
        verify(emailService, atLeast(1)).sendSimpleMessage(userMeetupOne.getUser().getEmail(),
                                                           EmailConstants.USER_REGISTERED,
                                                           EmailConstants.USER_REGISTERED_TEXT);
    }

    @Test
    void shouldCheckinUser() {
        UserMeetup userMeetup = createUserMeetup();

        userMeetupService.checkInUser(userMeetup);

        verify(userMeetupRepository, atLeast(1)).save(userMeetup);
        verify(emailService, atLeast(1)).sendSimpleMessage(userMeetup.getUser().getEmail(),
                                                           EmailConstants.USER_CHECKED_IN,
                                                           EmailConstants.USER_CHECKED_IN_TEXT);
    }

    @Test
    void shouldRegisterAttendance() {
        UserMeetup userMeetup = createUserMeetup();

        userMeetupService.userAttended(userMeetup);

        verify(userMeetupRepository, atLeast(1)).save(userMeetup);
        verify(emailService, atLeast(1)).sendSimpleMessage(userMeetup.getUser().getEmail(),
                                                           EmailConstants.USER_ATTENDED,
                                                           EmailConstants.USER_ATTENDED_TEXT);
    }

    @Test
    void shouldShowAllMeetupRegistrations() {
        UserMeetup userMeetup = createUserMeetup();

        when(userMeetupRepository.findAll()).thenReturn(Arrays.asList(userMeetup));

        List<UserMeetup> userMeetups = userMeetupService.showAllMeetupsRegistrations();

        verify(userMeetupRepository, atLeast(1)).findAll();
        assertThat(userMeetups).isEqualTo(Arrays.asList(userMeetup));
    }
}
