package com.santander.birras.transformers.toDomain;

import com.santander.birras.client.Forecast;
import com.santander.birras.client.WeatherClientResponse;
import com.santander.birras.domain.Weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class WeatherResponseTransformer {
    private static final Logger LOG = LoggerFactory.getLogger(WeatherResponseTransformer.class);

    public Weather transform(WeatherClientResponse weatherClientResponse, String localDate) {
        if (weatherClientResponse.getCity() != null) {
            List<Forecast> forecasts = weatherClientResponse.getForecastList();

            int forecastForRequiredDate = getRequiredForecast(forecasts, localDate);
            Forecast desiredForecast = forecasts.get(forecastForRequiredDate);
            return Weather.builder()
                    .cityName(weatherClientResponse.getCity().getName())
                    .date(getLocalDateFromString(weatherClientResponse.getForecastList()
                                                         .get(forecastForRequiredDate)
                                                         .getDate()))
                    .temperature(desiredForecast.getDailyForecast().getTempCelsius())
                    .temperatureMax(desiredForecast.getDailyForecast().getTempMax())
                    .temperatureMin(desiredForecast.getDailyForecast().getTempMin()).build();
        } else {
            return Weather.builder().build();
        }
    }

    private int getRequiredForecast(List<Forecast> forecasts, String localDate) {
        int count = 0;
        for (Forecast forecast : forecasts) {
            if (getLocalDateFromString(forecast.getDate()).equals(getLocalDateFromString(localDate))) {
                //quedarse con la el pronóstico que viene a las 18hs
                if (forecast.getDate().contains("18:00:00")) {
                    return count;
                }
            }
            count++;
        }
        return 0;
    }

    private LocalDate getLocalDateFromString(String localDate) {
        return LocalDate.parse(localDate.substring(0, 10));
    }
}
