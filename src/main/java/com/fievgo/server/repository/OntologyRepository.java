package com.fievgo.server.repository;

import com.fievgo.server.dto.ConditionReqDto;
import com.fievgo.server.dto.FlyScheduleResDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class OntologyRepository {
    // TODO: GraphDB 연결.
    public boolean checkHasCondition(Long memberId) {
        //TODO : 온톨로지에서 해당 멤버가 컨디션을 가지고 있는지 체크
        return false;
    }

    public void inputMemberCondition(ConditionReqDto conditionReqDto) {
        //TODO: 온톨로지에 해당 멤버의 컨디션을 업데이트 합니다.
    }

    public List<FlyScheduleResDto> getMemberSchedule(Long memberId) {
        //TODO: 온톨로지에서 해당 멤버의 스케줄을 검색
        FlyScheduleResDto flyScheduleResDto1 = new FlyScheduleResDto();
        flyScheduleResDto1.setStartAirport("출발공항1");
        flyScheduleResDto1.setStartTime("2023-11-23T14:53:00");
        flyScheduleResDto1.setEndAirport("도착공항1");
        flyScheduleResDto1.setEndTime("2023-11-23T15:53:00");
        flyScheduleResDto1.setCaptain("준커");
        flyScheduleResDto1.setFirstOfficer("함스");
        flyScheduleResDto1.setMechanic("보잉키킴");

        FlyScheduleResDto flyScheduleResDto2 = new FlyScheduleResDto();
        flyScheduleResDto2.setStartAirport("출발공항1");
        flyScheduleResDto2.setStartTime("2023-11-23T14:53:00");
        flyScheduleResDto2.setEndAirport("도착공항1");
        flyScheduleResDto2.setEndTime("2023-11-23T15:53:00");
        flyScheduleResDto2.setCaptain("준커");
        flyScheduleResDto2.setFirstOfficer("함스");
        flyScheduleResDto2.setMechanic("보잉키킴");

        FlyScheduleResDto flyScheduleResDto3 = new FlyScheduleResDto();
        flyScheduleResDto3.setStartAirport("출발공항1");
        flyScheduleResDto3.setStartTime("2023-11-23T14:53:00");
        flyScheduleResDto3.setEndAirport("도착공항1");
        flyScheduleResDto3.setEndTime("2023-11-23T15:53:00");
        flyScheduleResDto3.setCaptain("준커");
        flyScheduleResDto3.setFirstOfficer("함스");
        flyScheduleResDto3.setMechanic("보잉키킴");

        List<FlyScheduleResDto> result = new ArrayList<>();
        result.add(flyScheduleResDto1);
        result.add(flyScheduleResDto2);
        result.add(flyScheduleResDto3);
        return result;
    }
}
