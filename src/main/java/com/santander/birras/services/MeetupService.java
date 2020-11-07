package com.santander.birras.services;

import com.santander.birras.domain.Meetup;

import java.util.Optional;

public interface MeetupService {
    Optional<Meetup> findMeetupById(Long id);

    void addMeetup(Meetup meetup);
}
