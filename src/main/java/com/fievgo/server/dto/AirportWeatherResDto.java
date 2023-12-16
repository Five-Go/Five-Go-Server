package com.fievgo.server.dto;

import com.fievgo.server.enums.WeatherEn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirportWeatherResDto {
    private String weather;
    private String airport;

    public static AirportWeatherResDto of(String weather, String airport) {
        String prefix = "http://www.semanticweb.org/fivego#";
        return AirportWeatherResDto.builder()
                .weather(prefix + convertEnName(weather))
                .airport(prefix + airport)
                .build();
    }

    private static String convertEnName(String weather) {
        for (WeatherEn value : WeatherEn.values()) {
            if (value.toString().equals(weather)) {
                return value.getEnName();
            }
        }
        return null;
    }
}
