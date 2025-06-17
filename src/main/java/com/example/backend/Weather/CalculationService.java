package com.example.backend.Weather;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;


@Service
public class CalculationService {
    private final double panelsEffectiveness = 0.2;
    private final double installationPower = 2500.0;
    public double calculateAverage(List<Double> Vals){
        double sum = Vals.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        int n = Vals.size();
        return sum/(double)n;
    }

    public Boolean checkIfRains(List<Double> Days){
        long num =  Days.stream()
                .filter(c -> c >= 0.5)
                .count();
        return num >= 4;
    }

    public Pair<Double, Double> getMinMax(List<Double> Mins, List<Double> Maxs){
        return Pair.of(Collections.min(Mins), Collections.max(Maxs));
    }

    public String convertTime(long seconds) {
        return Duration.ofSeconds(seconds)
                .toString()                    // convert to string
                .substring(2)                  // remove "PT" prefix
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")  // add space between values
                .toLowerCase();               // convert to lowercase
    }

    public double calcEnergy(double sunshine){
        return panelsEffectiveness * sunshine/3600.0 * installationPower;
    }
}
