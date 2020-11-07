package com.santander.birras.services.impl;

import com.santander.birras.constants.EmailConstants;
import com.santander.birras.domain.Meetup;
import com.santander.birras.domain.Weather;
import com.santander.birras.exceptions.WeatherServiceException;
import com.santander.birras.repositories.MeetupRepository;
import com.santander.birras.services.MeetupService;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MeetupServiceImpl implements MeetupService {

    private final WeatherServiceImpl weatherService;
    private final MeetupRepository meetupRepository;
    private final EmailServiceImpl emailService;

    public MeetupServiceImpl(WeatherServiceImpl weatherService,
                             MeetupRepository meetupRepository,
                             EmailServiceImpl emailService) {
        this.weatherService = weatherService;
        this.meetupRepository = meetupRepository;
        this.emailService = emailService;
    }

    public Optional<Meetup> findMeetupById(Long id) {
        return meetupRepository.findById(id);
    }

    @Override
    public void addMeetup(Meetup meetup) {
        meetupRepository.save(meetup);

        emailService.sendSimpleMessage(EmailConstants.ADMIN_EMAIL, EmailConstants.MEETUP_CREATED, EmailConstants.MEETUP_CREATED_TEXT);
    }

    public double getNumberOfBeerBoxes(Optional<Meetup> meetup) throws WeatherServiceException {
        Meetup m = meetup.get();
        Weather weather = weatherService.getWeatherForecast(m.getCity(), "Argentina", m.getDateTime());
        return calculate(weather.getTemperatureMax(), m.getUsers().size());
    }

    private double calculate(double temperature, int users) {
        double qty = 0;

        if (temperature >= 20 && temperature < 24) {
            qty = users;
        } else if (temperature < 20) {
            qty = users * 0.75;
        } else if (temperature >= 24) {
            qty = users * 3;
        }
        return Math.ceil(qty / 6);
    }
}
