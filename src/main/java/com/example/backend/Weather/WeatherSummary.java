package com.example.backend.Weather;
import org.apache.commons.lang3.tuple.Pair;
public record WeatherSummary(String avgSunlight, Double avgPressure, Double tempMin, Double tempMax, String rain) {
}
