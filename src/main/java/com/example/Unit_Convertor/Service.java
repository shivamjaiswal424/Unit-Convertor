package com.example.Unit_Convertor;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class Service {


    // Coefficients relative to 1 meter
    private static final Map<String, Double> LENGTH_UNITS = Map.of(
            "mm", 0.001,
            "cm", 0.01,
            "m", 1.0,
            "km", 1000.0,
            "in", 0.0254,
            "ft", 0.3048,
            "yd", 0.9144,
            "mi", 1609.344
    );


    // Coefficients relative to 1 gram
    private static final Map<String, Double> WEIGHT_UNITS = Map.of(
            "mg", 0.001,
            "g", 1.0,
            "kg", 1000.0,
            "oz", 28.3495,
            "lb", 453.592
    );

    public Boolean checkLengthUnit(String unit){
        return LENGTH_UNITS.containsKey(unit);
    }

    public Boolean checkWeightUnit(String unit){
        return WEIGHT_UNITS.containsKey(unit);
    }

    public Boolean checkTemperatureUnit(String unit){
        return TEMPERATURE_UNITS.contains(unit);
    }

    private static final List<String> TEMPERATURE_UNITS = List.of("C", "F", "K");
    

    public Map<String, Double> getLengthUnits() {
        return LENGTH_UNITS;
    }


    public Map<String, Double> getWeightUnits() {
        return WEIGHT_UNITS;
    }


    public List<String> getTemperatureUnits() {
        return TEMPERATURE_UNITS;
    }


    public double convertLength(String from, String to, Double value) {
        Double valueInMeter = value * LENGTH_UNITS.get(from);
        return valueInMeter / LENGTH_UNITS.get(to);
    }


    public double convertWeight(String from, String to, double value) {
        validate(from, to, WEIGHT_UNITS);
        double valueInGrams = value * WEIGHT_UNITS.get(from);
        return valueInGrams / WEIGHT_UNITS.get(to);
    }


    public double convertTemperature(String from, String to, double value) {
        if (!TEMPERATURE_UNITS.contains(from) || !TEMPERATURE_UNITS.contains(to)) {
            throw new IllegalArgumentException("Invalid temperature unit(s)");
        }
// to Celsius
        double celsius;
        switch (from) {
            case "C" -> celsius = value;
            case "F" -> celsius = (value - 32) * 5.0 / 9.0;
            case "K" -> {
                if (value < 0) throw new IllegalArgumentException("Kelvin cannot be negative");
                celsius = value - 273.15;
            }
            default -> throw new IllegalArgumentException("Invalid temperature unit");
        }
// from Celsius to target
        return switch (to) {
            case "C" -> celsius;
            case "F" -> celsius * 9.0 / 5.0 + 32.0;
            case "K" -> celsius + 273.15;
            default -> throw new IllegalArgumentException("Invalid temperature unit");
        };
    }


    private void validate(String from, String to, Map<String, Double> map) {
        if (!map.containsKey(from) || !map.containsKey(to)) {
            throw new IllegalArgumentException("Invalid unit(s): " + from + ", " + to);
        }
    }
}