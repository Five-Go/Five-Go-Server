package com.fievgo.server.service;

import com.fievgo.server.domain.Airport;
import com.fievgo.server.dto.FlyScheduleResDto;
import com.fievgo.server.repository.AirportRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;

    public void changeAirportNameFromDto(List<FlyScheduleResDto> dtos) {
        List<Airport> allAirport = airportRepository.findAll();
        Map<Long, String> airportIdToNameMap = allAirport.stream()
                .collect(Collectors.toMap(Airport::getId, Airport::getName));

        dtos
                .forEach(dto -> {
                    dto.changeStartAirport(
                            airportIdToNameMap.get(Long.parseLong(dto.getStartAirport().split("port")[1])));
                    dto.changeEndAirport(
                            airportIdToNameMap.get(Long.parseLong(dto.getEndAirport().split("port")[1])));
                });
    }

    public void changeAirportNameFromDto(FlyScheduleResDto dto) {
        List<Airport> allAirport = airportRepository.findAll();
        Map<Long, String> airportIdToNameMap = allAirport.stream()
                .collect(Collectors.toMap(Airport::getId, Airport::getName));

        dto.changeStartAirport(airportIdToNameMap.get(Long.parseLong(dto.getStartAirport().split("port")[1])));
        dto.changeEndAirport(airportIdToNameMap.get(Long.parseLong(dto.getEndAirport().split("port")[1])));
    }
}
