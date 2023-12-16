package com.fievgo.server.service;

import com.fievgo.server.dto.WeatherConditionDto;
import com.fievgo.server.repository.OntologyRepository;
import com.fievgo.server.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final OntologyRepository ontologyRepository;

    public WeatherConditionDto getAirPortWeather(String airportName) {
        WeatherConditionDto airportWeather = weatherRepository.getAirPortWeather(airportName);
        ontologyRepository.deleteAirportWeather(airportName);
        ontologyRepository.saveAirportWeather(airportWeather, airportName);
        ontologyRepository.saveAirportCondition(getAirportCondition(airportWeather), airportName);
        return airportWeather;
    }

    private int getAirportCondition(WeatherConditionDto airPortWeather) {
        int condition = 1;
        if (airPortWeather.getTotalWeather().split(", ").length > 1) {
            condition++;
        }
        return condition + airPortWeather.getTotalCondition();
    }
}
