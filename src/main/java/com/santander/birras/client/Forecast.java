package com.santander.birras.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Forecast {

    @JsonProperty("dt")
    String dt;
    @JsonProperty("main")
    DailyForecast dailyForecast;
    @JsonProperty("dt_txt")
    String date;
}
