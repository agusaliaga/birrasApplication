package com.santander.birras.services;

import com.santander.birras.domain.Weather;
import com.santander.birras.repositories.WeatherRepository;
import com.santander.birras.services.impl.WeatherServiceImpl;
import com.santander.birras.transformers.toDomain.WeatherResponseTransformer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;

import static com.santander.birras.TestData.createWeather;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class WeatherServiceImplTest {

    @Mock
    private WeatherRepository weatherRepository;
    @Mock
    private WeatherResponseTransformer weatherResponseTransformer;
    @Mock
    private RestTemplate restTemplate;

    private WeatherServiceImpl weatherService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        weatherService = new WeatherServiceImpl(weatherRepository, weatherResponseTransformer);
    }

    @Test
    void shouldNotSaveWeatherInDBIfItAlreadyExists() {
        Weather weather = createWeather();

        Weather newWeather = Weather.builder()
                .temperatureMax(30)
                .cityName("Cordoba")
                .date(LocalDate.parse("2020-11-03"))
                .build();

        when(weatherRepository.findAll()).thenReturn(Arrays.asList(weather));

        weatherService.saveWeather(newWeather);

        verify(weatherRepository, never()).save(newWeather);

    }

    @Test
    void shouldSaveWeatherInDBIfItDoesntExist() {
        Weather weather = createWeather();

        Weather newWeather = Weather.builder()
                .temperatureMax(30)
                .cityName("Cordoba")
                .date(LocalDate.parse("2020-11-04"))
                .build();

        when(weatherRepository.findAll()).thenReturn(Arrays.asList(weather));

        weatherService.saveWeather(newWeather);

        verify(weatherRepository, atLeast(1)).save(newWeather);
    }
}
