package com.example.backend.Weather;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@Service
public class WeatherService {

    private final WebClient webClient;
    private final CalculationService calculationService;

    public WeatherService(WebClient.Builder webClientBuilder, CalculationService calculationService) {
        this.webClient = webClientBuilder.baseUrl("https://api.open-meteo.com/v1/forecast").build();
        this.calculationService = calculationService;
    }

    public Mono<WeatherData> fetchWeather(double latitude, double longitude) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam("daily", "weathercode,temperature_2m_max,temperature_2m_min,sunshine_duration,precipitation_sum")
                        .queryParam("hourly", "pressure_msl")
                        .queryParam("timezone", "Europe/Berlin")
                        .build())
                .retrieve()
                .bodyToMono(WeatherData.class);


    }
    public Mono<WeatherSummary> fetchWeatherSummary(double latitude, double longitude){
        return fetchWeather(latitude, longitude)
                .map(weatherData -> {

                    double avgSunlight = calculationService.calculateAverage(weatherData.getDaily().getSunshine());
                    double avgPressure = calculationService.calculateAverage(weatherData.getHourly().getPressure());
                    avgPressure = Math.round(avgPressure * 100.0) / 100.0;

                    Pair<Double, Double> tempRange = calculationService.getMinMax(weatherData.getDaily().getTempMin(),
                            weatherData.getDaily().getTempMax());

                    boolean rain = calculationService.checkIfRains(weatherData.getDaily().getRain());
                    String sunlightConverted = calculationService.convertTime((int) avgSunlight);

                    return new WeatherSummary(sunlightConverted, avgPressure, tempRange.getKey(), tempRange.getValue(), rain);
                });
    }

    public Mono<List<DailyWeather>> fetchAllWeek(double latitude, double longitude){
        return fetchWeather(latitude, longitude)
                .map(weatherData -> {
                    int len = weatherData.getDaily().getTempMin().size();

                    List<String> dates = weatherData.getDaily().getTime();
                    List<Integer> codes = weatherData.getDaily().getWeathercode();
                    List<Double> temp_mins = weatherData.getDaily().getTempMin();
                    List<Double> temp_maxs = weatherData.getDaily().getTempMax();
                    List<Double> sunshines = weatherData.getDaily().getSunshine();

                    List<DailyWeather> result = new ArrayList<>();
                    for (int i = 0; i < len; i++){
                        result.add(new DailyWeather(dates.get(i), codes.get(i), temp_mins.get(i), temp_maxs.get(i),
                                Math.round(calculationService.calcEnergy(sunshines.get(i) * 100.0)) / 100.0));
                    }
                    return result;
                });
    }
}

