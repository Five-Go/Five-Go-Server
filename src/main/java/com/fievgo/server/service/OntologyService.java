package com.fievgo.server.service;

import static com.fievgo.server.enums.FactorWeight.AIRCRAFT_WEIGHT;
import static com.fievgo.server.enums.FactorWeight.AIRPORT_WEIGHT;
import static com.fievgo.server.enums.FactorWeight.CAPTAIN_WEIGHT;
import static com.fievgo.server.enums.FactorWeight.FIRST_OFFICER_WEIGHT;
import static com.fievgo.server.enums.FactorWeight.MECHANIC_WEIGHT;

import com.fievgo.server.dto.ConditionReqDto;
import com.fievgo.server.dto.FactorAndCondtionDto;
import com.fievgo.server.dto.FactorWeightResDto;
import com.fievgo.server.dto.FlyScheduleResDto;
import com.fievgo.server.dto.ScheduleConditionDto;
import com.fievgo.server.repository.OntologyRepository;
import java.util.Comparator;
import java.util.HashMap;
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

    public List<FactorWeightResDto> getTopDangerFactor(String scheduleId) {
        HashMap<String, Long> factorWeight = new HashMap<>();
        // 1. 스케줄의 모든 요인을 검색해서 HashMap 생성
        List<String> allFactors = ontologyRepository.getAllFactorBySchedule(scheduleId);
        allFactors.forEach(factor -> factorWeight.put(factor, 0L));

        // 2. 기장의 요인 검색
        FactorAndCondtionDto captainFactors = ontologyRepository.getFactorBySchedule("기장", scheduleId);
        // 3. 부기장의 요인 검색
        FactorAndCondtionDto firstOfficerFactors = ontologyRepository.getFactorBySchedule("부기장", scheduleId);
        // 4. 정비사의 요인 검색
        FactorAndCondtionDto mechanicFactors = ontologyRepository.getFactorBySchedule("정비사", scheduleId);
        // 5. 비행기의 요인 검색
        FactorAndCondtionDto aircraftFactors = ontologyRepository.getFactorBySchedule("비행기", scheduleId);
        // 7. 공항의 요인 검색
        FactorAndCondtionDto airportFactors = ontologyRepository.getFactorBySchedule("공항", scheduleId);

        reflectWeightFactor(factorWeight, captainFactors, CAPTAIN_WEIGHT.getWeight());
        reflectWeightFactor(factorWeight, firstOfficerFactors, FIRST_OFFICER_WEIGHT.getWeight());
        reflectWeightFactor(factorWeight, mechanicFactors, MECHANIC_WEIGHT.getWeight());
        reflectWeightFactor(factorWeight, aircraftFactors, AIRCRAFT_WEIGHT.getWeight());
        reflectWeightFactor(factorWeight, airportFactors, AIRPORT_WEIGHT.getWeight());

        return factorWeight.entrySet().stream()
                .map(entry -> FactorWeightResDto.of(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(FactorWeightResDto::getWeight).reversed())
                .toList();
    }

    private void reflectWeightFactor(HashMap<String, Long> factorWeight, FactorAndCondtionDto factors,
                                     int weight) {
        if (factors.getCondition() != -1) {
            int finalWeight = Integer.parseInt(factors.getCondition().toString()) * weight;

            for (String factor : factors.getFactors()) {
                Long nowWeight = factorWeight.get(factor);
                factorWeight.put(factor, nowWeight + finalWeight);
            }
        }
    }
}
