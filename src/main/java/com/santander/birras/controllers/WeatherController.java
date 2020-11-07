package com.santander.birras.controllers;

import com.santander.birras.domain.Meetup;
import com.santander.birras.domain.Weather;
import com.santander.birras.exceptions.WeatherServiceException;
import com.santander.birras.services.impl.MeetupServiceImpl;
import com.santander.birras.services.impl.WeatherServiceImpl;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Weather Controller")
@RestController
@EnableCircuitBreaker
@RequestMapping("/api/birras")
public class WeatherController {

    private final WeatherServiceImpl weatherService;
    private final MeetupServiceImpl meetupService;

    public WeatherController(WeatherServiceImpl weatherService,
                             MeetupServiceImpl meetupService) {
        this.weatherService = weatherService;
        this.meetupService = meetupService;
    }

    /**
     * CONSULTAR LA TEMPERATURA DEL DIA DE LA MEETING INTRODUCIENDO LUGAR, PROVINCIA, Y FECHA DE LA MEETING
     */
    @ApiOperation(value = "Retrieve temperature by location and date, (within the next 5 days)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved temperature"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach was not found"),
            @ApiResponse(code = 500, message = "An internal error has occurred")
    })
    @GetMapping(value = "/weather/{country}/{province}/{localdate}")
    public Weather getWeatherFor(@PathVariable String country,
                                 @PathVariable String province,
                                 @PathVariable String localdate) throws
            WeatherServiceException {

        Weather weather = weatherService.getWeatherForecast(country, province, localdate);
        weatherService.saveWeather(weather);
        return weather;
    }

    /**
     * CONSULTAR LA TEMPERATURA DEL DIA DE LA MEETING INTRODUCIENDO LA ID DE LA MEETING
     */
    @ApiOperation(value = "Retrieve temperature by meetup Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved temperature for the day of the meetup"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach was not found"),
            @ApiResponse(code = 500, message = "An internal error has occurred")
    })
    @GetMapping(value = "/weather/meetup/{meetupId}")
    public Weather getWeatherFor(@PathVariable Long meetupId) throws WeatherServiceException {

        Optional<Meetup> m = meetupService.findMeetupById(meetupId);
        Meetup selectedMeetup = m.get();
        Weather weather = weatherService.getWeatherForecast("Argentina",
                                                            selectedMeetup.getCity(),
                                                            selectedMeetup.getDateTime());
        weatherService.saveWeather(weather);
        return weather;
    }
}
