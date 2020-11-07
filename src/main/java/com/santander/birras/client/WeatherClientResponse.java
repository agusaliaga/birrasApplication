package com.santander.birras.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class WeatherClientResponse {

    String code;
    String cnt;
    @JsonProperty("list")
    List<Forecast> forecastList;
    @JsonProperty("city")
    City city;
}
