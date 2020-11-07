package com.santander.birras.controllers;

import com.santander.birras.domain.Meetup;
import com.santander.birras.domain.Weather;
import com.santander.birras.exceptions.WeatherServiceException;
import com.santander.birras.services.impl.MeetupServiceImpl;
import com.santander.birras.services.impl.WeatherServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static com.santander.birras.TestData.createMeetup;
import static com.santander.birras.TestData.createWeather;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class WeatherControllerTest {

    @Mock
    private WeatherServiceImpl weatherService;
    @Mock
    private MeetupServiceImpl meetupService;

    private WeatherController weatherController;

    @BeforeEach
    void setUp() {
        initMocks(this);
        weatherController = new WeatherController(weatherService, meetupService);
    }

    @Test
    public void shouldGetWeatherForCountryProvinceAndLocaldate() throws WeatherServiceException {
        Weather weather = createWeather();

        when(weatherService.getWeatherForecast("Argentina", "Cordoba", "2020-11-03")).thenReturn(weather);

        weatherController.getWeatherFor("Argentina", "Cordoba", "2020-11-03");

        verify(weatherService).getWeatherForecast("Argentina", "Cordoba", "2020-11-03");
        verify(weatherService).saveWeather(weather);
    }

    @Test
    public void shouldGetWeatherForMeetupId() throws WeatherServiceException {
        Weather weather = createWeather();
        Meetup meetup = createMeetup();

        when(weatherService.getWeatherForecast("Argentina",
                                               meetup.getCity(),
                                               meetup.getDateTime())).thenReturn(weather);
        when(meetupService.findMeetupById(1L)).thenReturn(Optional.of(meetup));

        weatherController.getWeatherFor(1L);

        verify(meetupService).findMeetupById(1L);
        verify(weatherService).getWeatherForecast("Argentina", meetup.getCity(), meetup.getDateTime());
        verify(weatherService).saveWeather(weather);
    }
}
