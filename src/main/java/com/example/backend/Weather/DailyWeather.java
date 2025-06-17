package com.example.backend.Weather;

public record DailyWeather(String date, Integer code, Double tempMin, Double tempMax, Double energy){
}
