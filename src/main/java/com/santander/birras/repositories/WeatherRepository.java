package com.santander.birras.repositories;

import com.santander.birras.domain.Weather;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface WeatherRepository extends CrudRepository<Weather, Long> {
    Weather findByDate(LocalDate localDate);
}
