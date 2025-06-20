package com.example.backend.Weather;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


//@CrossOrigin(origins = {"http://localhost:3000", "https://recrutationfrontend.fly.dev"})

@CrossOrigin(origins = "https://recrutationfrontend.fly.dev")
@RestController
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather-summary")
    public Mono<WeatherSummary> getWeather(@RequestParam double lat, @RequestParam double lon) {
        return weatherService.fetchWeatherSummary(lat, lon);
        //return weatherService.fetchWeather(lat, lon);
    }
    //http://localhost:8080/weather-summary?lat=20.099&lon=30.095

    @GetMapping("/weather-daytoday")
    public Mono<List<DailyWeather>> getWeatherDaily(@RequestParam double lat, @RequestParam double lon) {
        return weatherService.fetchAllWeek(lat, lon);
    }
    //http://localhost:8080/weather-daytoday?lat=20.099&lon=30.095
}
