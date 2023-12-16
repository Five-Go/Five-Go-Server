package com.fievgo.server.repository;

import com.fievgo.server.dto.AirPortNxNyDto;
import com.fievgo.server.dto.WeatherConditionDto;
import com.fievgo.server.utils.OpenApiConnection;
import com.fievgo.server.utils.StringMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class WeatherRepository {
    private final OntologyRepository ontologyRepository;
    private final OpenApiConnection openApiConnection;

    public WeatherConditionDto getAirPortWeather(String airPortName) {
        AirPortNxNyDto dto = ontologyRepository.getAirPortNxAndNyByName(airPortName);
        String result = openApiConnection.connectionWithWeatherAPI(dto);
        return StringMapper.convertToWeatherDto(result);
    }
}
