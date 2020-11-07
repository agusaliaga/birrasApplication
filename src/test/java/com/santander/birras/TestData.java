package com.santander.birras;

import com.santander.birras.client.City;
import com.santander.birras.client.DailyForecast;
import com.santander.birras.client.Forecast;
import com.santander.birras.client.WeatherClientResponse;
import com.santander.birras.domain.Meetup;
import com.santander.birras.domain.User;
import com.santander.birras.domain.UserMeetup;
import com.santander.birras.domain.Weather;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestData {

    public static ArrayList<User> createUsers() {
        User userOne = User.builder()
                .userName("agustina")
                .password("agustina")
                .email("test@gmail.com")
                .id(1L)
                .build();

        User userTwo = User.builder()
                .userName("test")
                .password("test")
                .email("test@gmail.com")
                .id(2L)
                .build();

        ArrayList<User> users = new ArrayList<>();
        users.add(userOne);
        users.add(userTwo);

        return users;
    }

    public static Meetup createMeetup() {
        return Meetup.builder()
                .city("Cordoba")
                .dateTime("2020-11-03")
                .description("Java & SpringBoot")
                .id(1L)
                .build();
    }

    public static Weather createWeather() {
        return Weather.builder()
                .temperatureMax(30)
                .cityName("Cordoba")
                .date(LocalDate.parse("2020-11-03"))
                .build();
    }

    public static Set<UserMeetup> createUserMeetups() {
        UserMeetup userMeetupOne = UserMeetup.builder().meetup(createMeetup()).user(createUsers().get(0)).build();
        UserMeetup userMeetupTwo = UserMeetup.builder().meetup(createMeetup()).user(createUsers().get(1)).build();

        Set<UserMeetup> userMeetups = new HashSet<>();
        userMeetups.add(userMeetupOne);
        userMeetups.add(userMeetupTwo);

        return userMeetups;
    }

    public static UserMeetup createUserMeetup() {
        return UserMeetup.builder().meetup(createMeetup()).user(createUsers().get(0)).build();
    }

    public static WeatherClientResponse createWeatherClientResponse() {

        DailyForecast dailyForecast = DailyForecast.builder()
                .tempCelsius(30.0)
                .tempMax(30.0)
                .tempMin(30.0)
                .build();
        Forecast forecast = Forecast.builder().date("2020-11-03").dailyForecast(dailyForecast).build();
        City city = City.builder().country("Argentina").name("Cordoba").build();

        return WeatherClientResponse.builder()
                .city(city)
                .forecastList(Arrays.asList(forecast))
                .build();
    }
}
