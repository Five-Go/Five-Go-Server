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
public class ScheduleConditionDto {
    private String captainCondition;
    private String firstOfficerCondition;
    private String mechanicCondition;
    private String aircraftCondition;

    public static ScheduleConditionDto of(HashMap<String, String> data) {
        String captainCondition = data.get("CaptainCondition");
        String firstOfficerCondition = data.get("FirstOfficerCondition");
        String mechanicCondition = data.get("MechanicCondition");
        String aircraftCondition = data.get("AircraftCondition");

        return ScheduleConditionDto.builder()
                .captainCondition(captainCondition.equals("-1") ? "미입력" : captainCondition)
                .firstOfficerCondition(firstOfficerCondition.equals("-1") ? "미입력" : firstOfficerCondition)
                .mechanicCondition(mechanicCondition.equals("-1") ? "미입력" : mechanicCondition)
                .aircraftCondition(aircraftCondition.equals("-1") ? "미확인" : aircraftCondition)
                .build();

    }
}
