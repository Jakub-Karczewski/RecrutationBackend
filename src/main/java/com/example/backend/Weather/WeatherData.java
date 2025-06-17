package com.example.backend.Weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WeatherData {

    private Daily daily;
    private Hourly hourly;

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Daily {

        //weathercode,temperature_2m_max,temperature_2m_min,sunshine_duration,pressure_mean_sea_level

        @JsonProperty("temperature_2m_max")
        private List<Double> tempMax;
        @JsonProperty("temperature_2m_min")
        private List<Double> tempMin;
        @JsonProperty("weathercode")
        private List<Integer> weathercode;
        @JsonProperty("time")
        private List<String> time;
        @JsonProperty("sunshine_duration")
        private List<Double> sunshine;
        @JsonProperty("precipitation_sum")
        private List<Double> rain;
    }
    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Hourly{
        @JsonProperty("pressure_msl")
        private List<Double> pressure;
    }
}
