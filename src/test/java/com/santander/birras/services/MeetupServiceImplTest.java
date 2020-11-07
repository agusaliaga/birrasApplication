package com.santander.birras.services;

import com.santander.birras.constants.EmailConstants;
import com.santander.birras.domain.Meetup;
import com.santander.birras.repositories.MeetupRepository;
import com.santander.birras.services.impl.EmailServiceImpl;
import com.santander.birras.services.impl.MeetupServiceImpl;
import com.santander.birras.services.impl.WeatherServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static com.santander.birras.TestData.createMeetup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MeetupServiceImplTest {

    @Mock
    private WeatherServiceImpl weatherService;
    @Mock
    private MeetupRepository meetupRepository;
    @Mock
    private EmailServiceImpl emailService;

    MeetupServiceImpl meetupService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        meetupService = new MeetupServiceImpl(weatherService, meetupRepository, emailService);
    }

    @Test
    void shouldFindMeetupById() {
        Meetup meetup = createMeetup();

        when(meetupRepository.findById(1L)).thenReturn(Optional.of(meetup));

        Optional<Meetup> returnedMeetup = meetupService.findMeetupById(1L);

        verify(meetupRepository, atLeast(1)).findById(1L);
        assertThat(returnedMeetup).isEqualTo(Optional.of(meetup));
    }

    @Test
    void shouldAddMeetup() {
        Meetup meetup = createMeetup();

        meetupService.addMeetup(meetup);

        verify(meetupRepository, atLeast(1)).save(meetup);
        verify(emailService, atLeast(1)).sendSimpleMessage(EmailConstants.ADMIN_EMAIL,
                                                           EmailConstants.MEETUP_CREATED,
                                                           EmailConstants.MEETUP_CREATED_TEXT);
    }
}
