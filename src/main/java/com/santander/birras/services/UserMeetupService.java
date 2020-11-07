package com.santander.birras.services;

import com.santander.birras.domain.UserMeetup;

import java.util.List;

public interface UserMeetupService {
    void addUserToMeetup(UserMeetup userMeetup);

    void checkInUser(UserMeetup userMeetup);

    void userAttended(UserMeetup userMeetup);

    List<UserMeetup> showAllMeetupsRegistrations();
}

