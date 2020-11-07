package com.santander.birras.repositories;

import com.santander.birras.domain.Meetup;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetupRepository extends CrudRepository<Meetup, Long> {
}

