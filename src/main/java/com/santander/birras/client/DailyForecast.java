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

public class DailyForecast {

    @JsonProperty("temp")
    Double tempCelsius;
    @JsonProperty("temp_min")
    Double tempMin;
    @JsonProperty("temp_max")
    Double tempMax;
}
