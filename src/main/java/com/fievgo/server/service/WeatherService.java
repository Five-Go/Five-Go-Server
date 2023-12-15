package com.fievgo.server.service;

import com.fievgo.server.dto.WeatherConditionDto;
import com.fievgo.server.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;

    public WeatherConditionDto getAirPortWeather(String airPortName) {
        return weatherRepository.getAirPortWeather(airPortName);
    }
}
