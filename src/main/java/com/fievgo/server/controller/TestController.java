package com.fievgo.server.controller;

import com.fievgo.server.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final WeatherService weatherRepository;

    @GetMapping("/test")
    public void test() {
        weatherRepository.getAirPortWeather("인천공항");
    }
}
