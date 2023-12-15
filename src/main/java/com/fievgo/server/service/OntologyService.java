package com.fievgo.server.service;

import com.fievgo.server.dto.ConditionReqDto;
import com.fievgo.server.dto.FlyScheduleResDto;
import com.fievgo.server.dto.ScheduleConditionDto;
import com.fievgo.server.repository.OntologyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OntologyService {
    private final OntologyRepository ontologyRepository;
    private final MemberService memberService;

    public boolean hasCondition(Long memberId) {
        return ontologyRepository.checkHasCondition(memberId);
    }

    public void inputMemberCondition(ConditionReqDto conditionReqDto) {
        ontologyRepository.inputMemberCondition(conditionReqDto);
    }

    public List<FlyScheduleResDto> getMemberSchedules(Long memberId) {
        List<FlyScheduleResDto> memberSchedules = ontologyRepository.getMemberSchedules(memberId);
        return memberService.convertMemberName(memberSchedules);
    }

    public FlyScheduleResDto getSchedule(String scheduleId) {
        FlyScheduleResDto schedule = ontologyRepository.getSchedule(scheduleId);
        return memberService.convertMemberName(schedule);
    }


    public ScheduleConditionDto getConditionBySchedule(String scheduleId) {
        return ontologyRepository.getConditionBySchedule(scheduleId);
    }
}
