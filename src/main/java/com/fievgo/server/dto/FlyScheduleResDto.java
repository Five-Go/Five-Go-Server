package com.fievgo.server.dto;

import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FlyScheduleResDto {
    private String schedule;
    private String startAirport;
    private String startTime;
    private String endAirport;
    private String endTime;
    private String aircraft;
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
                .aircraft(data.get("Aircraft"))
                .aircraftType(data.get("AircraftType"))
                .captain(data.get("Captain"))
                .firstOfficer(data.get("FirstOfficer"))
                .mechanic(data.get("Mechanic"))
                .build();

    }

    public void changeCaptainName(String name) {
        this.captain = name;
    }

    public void changeFirstOfficerName(String name) {
        this.firstOfficer = name;
    }

    public void changeMechanicName(String name) {
        this.mechanic = name;
    }

}
