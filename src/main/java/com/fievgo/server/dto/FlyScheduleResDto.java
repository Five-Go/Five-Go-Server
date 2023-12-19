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
                .schedule(data.get("Schedule").split("Schedule")[1])
                .startAirport(data.get("StartAirport"))
                .startTime(convertTimeFormat(data.get("StartTime")))
                .endAirport(data.get("EndAirport"))
                .endTime(convertTimeFormat(data.get("EndTime")))
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

    public void changeStartAirport(String name) {
        this.startAirport = name;
    }

    public void changeEndAirport(String name) {
        this.endAirport = name;
    }


    private static String convertTimeFormat(String time) {
        String[] dateTime = time.split("T");
        String[] dates = dateTime[0].split("-");
        String[] times = dateTime[1].split(":");

        return dates[1] + "." + dates[2] + " " + times[0] + ":" + times[1];
    }

}
