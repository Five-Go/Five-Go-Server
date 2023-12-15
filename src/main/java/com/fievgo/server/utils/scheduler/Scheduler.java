package com.fievgo.server.utils.scheduler;

import com.fievgo.server.dto.ConditionReqDto;
import com.fievgo.server.repository.MemberRepository;
import com.fievgo.server.service.OntologyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Transactional
@RequiredArgsConstructor
public class Scheduler {
    private final MemberRepository memberRepository;
    private final OntologyService ontologyService;

    /**
     * 모든 사람의 컨디션 초기화.
     */
    @Scheduled(cron = "0 0 0 * * *") // 초 분 시 일 월 요일
    public void initAllMemberCondition() {
        List<ConditionReqDto> dtos = memberRepository.findAll().stream()
                .map(member -> ConditionReqDto.of(member.getId(), -1L))
                .toList();
        for (ConditionReqDto dto : dtos) {
            ontologyService.inputMemberCondition(dto);
        }
    }
}
