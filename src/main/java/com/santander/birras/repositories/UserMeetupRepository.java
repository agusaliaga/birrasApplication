package com.santander.birras.repositories;

import com.santander.birras.domain.UserMeetup;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMeetupRepository extends CrudRepository<UserMeetup, Long> {
}

