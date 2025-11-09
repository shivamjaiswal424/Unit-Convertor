package com.example.Unit_Convertor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/convert")
@RequiredArgsConstructor
public class Controller {

    @Autowired
    private Service service; // your existing service

    // DTOs
    public record ConvertRequest(String from, String to, Double value) {}
    public record ConvertResponse(Double value, String from, String to, Double result) {}
    public record ErrorResponse(String message) {}

    @PostMapping("/length")
    public ResponseEntity<?> convertLength(@RequestBody ConvertRequest req) {
        try {
            if (!service.checkLengthUnit(req.from()) || !service.checkLengthUnit(req.to())) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Invalid length unit(s)"));
            }
            if (req.value() == null) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Value is required"));
            }
            double result = service.convertLength(req.from(), req.to(), req.value());
            return ResponseEntity.ok(new ConvertResponse(req.value(), req.from(), req.to(), result));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
        }
    }

    @PostMapping("/weight")
    public ResponseEntity<?> convertWeight(@RequestBody ConvertRequest req) {
        try {
            if (!service.checkWeightUnit(req.from()) || !service.checkWeightUnit(req.to())) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Invalid weight unit(s)"));
            }
            if (req.value() == null) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Value is required"));
            }
            double result = service.convertWeight(req.from(), req.to(), req.value());
            return ResponseEntity.ok(new ConvertResponse(req.value(), req.from(), req.to(), result));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
        }
    }

    @PostMapping("/temperature")
    public ResponseEntity<?> convertTemperature(@RequestBody ConvertRequest req) {
        try {
            if (!service.checkTemperatureUnit(req.from()) || !service.checkTemperatureUnit(req.to())) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Invalid temperature unit(s)"));
            }
            if (req.value() == null) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Value is required"));
            }
            double result = service.convertTemperature(req.from(), req.to(), req.value());
            return ResponseEntity.ok(new ConvertResponse(req.value(), req.from(), req.to(), result));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
        }
    }
}