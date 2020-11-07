package com.santander.birras.controllers;

import com.santander.birras.domain.Meetup;
import com.santander.birras.domain.Weather;
import com.santander.birras.exceptions.WeatherServiceException;
import com.santander.birras.repositories.MeetupRepository;
import com.santander.birras.services.impl.MeetupServiceImpl;
import com.santander.birras.services.impl.WeatherServiceImpl;
import com.santander.birras.transformers.toDomain.WeatherResponseTransformer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.santander.birras.TestData.createMeetup;
import static com.santander.birras.TestData.createUserMeetups;
import static com.santander.birras.TestData.createWeather;
import static com.santander.birras.TestData.createWeatherClientResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MeetupControllerTest {

    @Mock
    private WeatherServiceImpl weatherService;
    @Mock
    private MeetupServiceImpl meetupService;
    @Mock
    private MeetupRepository meetupRepository;
    @Mock
    private WeatherResponseTransformer weatherResponseTransformer;

    private MeetupController meetupController;

    @BeforeEach
    void setUp() {
        initMocks(this);
        meetupController = new MeetupController(meetupService);
    }

    @Test
    public void shouldReturnNumberOfBeerBoxes() throws WeatherServiceException {
        Meetup meetup = createMeetup();
        meetup.setUsers(createUserMeetups());
        Weather weather = createWeather();

        when(meetupRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(meetup));
        when(weatherResponseTransformer.transform(createWeatherClientResponse(), "2020-11-03")).thenReturn(weather);
        when(meetupService.findMeetupById(1L)).thenReturn(java.util.Optional.ofNullable(meetup));
        when(weatherService.getWeatherForecast("Argentina", "Cordoba", "2020-11-03")).thenReturn(weather);
        when(meetupService.getNumberOfBeerBoxes(Optional.of(meetup))).thenReturn(6.0);

        double numBeerBoxes = meetupController.getNumberOfBeerBoxesFor(1L);

        verify(meetupService).findMeetupById(1L);
        assertThat(numBeerBoxes).isEqualTo(6.0);
    }

    @Test
    void shouldCreateNewMeetupAndCallMeetupService() {

        Meetup meetup = createMeetup();
        ResponseEntity response = meetupController.addMeetup(meetup);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Meetup Created");
        verify(meetupService, atLeast(1)).addMeetup(meetup);
    }
}
