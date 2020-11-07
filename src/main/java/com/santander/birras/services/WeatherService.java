package com.santander.birras.services;

import com.santander.birras.domain.Weather;
import com.santander.birras.exceptions.WeatherServiceException;

public interface WeatherService {
    Weather getWeatherForecast(String province, String country, String localDate) throws WeatherServiceException;

    void recover() throws WeatherServiceException;
}
