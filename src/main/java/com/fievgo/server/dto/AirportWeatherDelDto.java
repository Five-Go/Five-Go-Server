package com.fievgo.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirportWeatherDelDto {
    private String deleteAirport;

    public static AirportWeatherDelDto of(String airport) {
        String prefix = "http://www.semanticweb.org/fivego#";
        return AirportWeatherDelDto.builder()
                .deleteAirport(prefix + airport)
                .build();
    }
}
