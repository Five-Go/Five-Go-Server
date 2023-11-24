package com.fievgo.server.dto;

import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlyScheduleResDto {
    private String schedule;
    private String startAirport;
    private String startTime;
    private String endAirport;
    private String endTime;
    private String aircraftType;
    private String captain;
    private String firstOfficer;
    private String mechanic;

    public static FlyScheduleResDto of(HashMap<String, String> data) {
        return FlyScheduleResDto.builder()
                .schedule(data.get("Schedule"))
                .startAirport(data.get("StartAirport"))
                .startTime(data.get("StartTime"))
                .endAirport(data.get("EndAirport"))
                .endTime(data.get("EndTime"))
                .aircraftType(data.get("AircraftType"))
                .captain(data.get("Captain"))
                .firstOfficer(data.get("FirstOfficer"))
                .mechanic(data.get("Mechanic"))
                .build();

    }

}
