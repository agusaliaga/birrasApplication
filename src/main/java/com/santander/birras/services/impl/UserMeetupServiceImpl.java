package com.santander.birras.services.impl;

import com.santander.birras.constants.EmailConstants;
import com.santander.birras.domain.UserMeetup;
import com.santander.birras.repositories.UserMeetupRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMeetupServiceImpl {

    private final UserMeetupRepository userMeetupRepository;
    private final EmailServiceImpl emailService;

    public UserMeetupServiceImpl(UserMeetupRepository userMeetupRepository,
                                 EmailServiceImpl emailService) {
        this.userMeetupRepository = userMeetupRepository;
        this.emailService = emailService;
    }

    public void addUserToMeetup(UserMeetup userMeetup) {
        userMeetupRepository.save(userMeetup);
        emailService.sendSimpleMessage(userMeetup.getUser().getEmail(), EmailConstants.USER_REGISTERED, EmailConstants.USER_REGISTERED_TEXT);
    }

    public void checkInUser(UserMeetup userMeetup) {
        userMeetup.setCheckIn(true);
        userMeetupRepository.save(userMeetup);
        emailService.sendSimpleMessage(userMeetup.getUser().getEmail(), EmailConstants.USER_CHECKED_IN, EmailConstants.USER_CHECKED_IN_TEXT);
    }

    public void userAttended(UserMeetup userMeetup) {
        userMeetup.setAttended(true);
        userMeetupRepository.save(userMeetup);
        emailService.sendSimpleMessage(userMeetup.getUser().getEmail(), EmailConstants.USER_ATTENDED, EmailConstants.USER_ATTENDED_TEXT);
    }

    public List<UserMeetup> showAllMeetupsRegistrations() {
        List<UserMeetup> userMeetup = new ArrayList<>();
        userMeetupRepository.findAll().forEach(userMeetup::add);
        return userMeetup;
    }
}
