package com.fievgo.server.enums;

public enum WeatherEn {
    강풍("StrongWind"),
    난기류("Turbulence"),
    뇌우("Thunderstorm"),
    눈("Snow"),
    비("Rain"),
    저기온("LowTemperature"),
    저시정("LowVisibility"),
    적락운("Altocumulus"),
    맑음("Sunny");
    private final String EnName;

    WeatherEn(String enName) {
        EnName = enName;
    }

    public String getEnName() {
        return EnName;
    }
}
