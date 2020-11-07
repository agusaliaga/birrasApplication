package com.santander.birras.transformers;

import com.santander.birras.client.WeatherClientResponse;
import com.santander.birras.domain.Weather;
import com.santander.birras.exceptions.WeatherServiceException;
import com.santander.birras.transformers.toDomain.WeatherResponseTransformer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.santander.birras.TestData.createWeatherClientResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class WeatherResponseTransformerTest {

    private WeatherResponseTransformer weatherResponseTransformer;

    @BeforeEach
    void setUp() {
        initMocks(this);
        weatherResponseTransformer = new WeatherResponseTransformer();
    }

    @Test
    public void shouldTransformWeatherClientResponseIntoWeatherDomainObject() throws WeatherServiceException {

        WeatherClientResponse weatherClientResponse = createWeatherClientResponse();
        Weather weather = weatherResponseTransformer.transform(weatherClientResponse, "2020-11-03");

        assertThat(weather.getDate().toString()).isEqualTo("2020-11-03");
        assertThat(weather.getTemperatureMax()).isEqualTo(30.0);
        assertThat(weather.getCityName()).isEqualTo("Cordoba");
    }
}
