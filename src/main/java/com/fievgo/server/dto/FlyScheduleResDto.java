package com.fievgo.server.dto;

import lombok.Data;

@Data
public class FlyScheduleResDto {
    private String startAirport;
    private String startTime;
    private String endAirport;
    private String endTime;
    private String aircraftType;
    private String captain;
    private String firstOfficer;
    private String mechanic;
}
