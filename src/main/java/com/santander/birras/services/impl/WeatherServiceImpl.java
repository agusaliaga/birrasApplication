package com.santander.birras.services.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.santander.birras.client.WeatherClientResponse;
import com.santander.birras.domain.Weather;
import com.santander.birras.exceptions.WeatherServiceException;
import com.santander.birras.repositories.WeatherRepository;
import com.santander.birras.services.WeatherService;
import com.santander.birras.transformers.toDomain.WeatherResponseTransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class WeatherServiceImpl implements WeatherService {
    private static final Logger LOG = LoggerFactory.getLogger(WeatherServiceImpl.class);
    private static final String ERROR_RETRIEVING_WEATHER = "Error retrieving weather.";
    private static final String KEY = "a452774f4e705202db98cd767dda8f31";

    //docs https://openweathermap.org/forecast5

    private final WeatherRepository weatherRepository;
    private final WeatherResponseTransformer weatherResponseTransformer;

    public WeatherServiceImpl(WeatherRepository weatherRepository,
                              WeatherResponseTransformer weatherResponseTransformer) {
        this.weatherRepository = weatherRepository;
        this.weatherResponseTransformer = weatherResponseTransformer;
    }

    protected static final HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    @HystrixCommand(fallbackMethod = "returnWeatherFromDB", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "8000")
    })
    @Retryable(maxAttempts = 2, include = WeatherServiceException.class, backoff = @Backoff(delay = 200, multiplier = 2))
    public Weather getWeatherForecast(String country, String province, String localDate) throws
            WeatherServiceException {
        try {

            String WEATHER_URL = "https://api.openweathermap.org/data/2.5/forecast?q=" + province + "," + country + "&appid=" + KEY + "&units=metric";

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> request = new HttpEntity<>("", getHeaders());
            ResponseEntity<WeatherClientResponse> result = restTemplate.exchange(URI.create(WEATHER_URL),
                                                                                 HttpMethod.GET,
                                                                                 request,
                                                                                 WeatherClientResponse.class);
            return weatherResponseTransformer.transform(Objects.requireNonNull(result.getBody()), localDate);
        } catch (ResourceAccessException e) {
            LOG.warn(ERROR_RETRIEVING_WEATHER + " {} retrying...", e.getMessage());
            throw new WeatherServiceException(ERROR_RETRIEVING_WEATHER);
        }
    }

    public void saveWeather(Weather weather) {
        List<Weather> weathers = new ArrayList<>();
        weatherRepository.findAll().forEach(weathers::add);
        int exists = 0;

        for (Weather w : weathers) {
            if (w.getDate().toString().equals(weather.getDate().toString())) {
                exists = 1;
                break;
            } else {
                exists = 0;
            }
        }
        if (exists == 0) {
            weatherRepository.save(weather);
        }
    }

    public Weather returnWeatherFromDB(String country, String province, String localDate) throws
            WeatherServiceException {

        LOG.warn(ERROR_RETRIEVING_WEATHER + " from service, looking up in database...");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(localDate, formatter);

        Weather weather = weatherRepository.findByDate(date);
        if (weather != null) {
            return weather;
        } else {
            LOG.warn(ERROR_RETRIEVING_WEATHER + " Couln't find weather data for that date");
            throw new WeatherServiceException("Couln't find weather data for that date");
        }
    }

    @Override
    @Recover
    public void recover() throws WeatherServiceException {
        LOG.error(ERROR_RETRIEVING_WEATHER);
        LOG.info("Please try later.");

        throw new WeatherServiceException("Error after retrying to retrieve weather, try later.");
    }
}
