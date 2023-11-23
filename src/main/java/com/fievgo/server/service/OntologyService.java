package com.fievgo.server.service;

import com.fievgo.server.domain.Member;
import com.fievgo.server.dto.ConditionReqDto;
import com.fievgo.server.dto.FlyScheduleResDto;
import com.fievgo.server.dto.LoginReqDto;
import com.fievgo.server.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public Long getMemberId(LoginReqDto model) {
        log.info("Member email : {}, Member pwd : {}", model.getEmail(), model.getPassword());
        Member byEmail = memberRepository.findByEmail(model.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 이메일입니다."));

        log.info("Search ->  Member email : {}, Member pwd : {}", byEmail.getEmail(), byEmail.getPassword());
        if (!(byEmail.getPassword().equals(model.getPassword()))) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return byEmail.getId();
    }

    public boolean hasCondition(Long memberId) {
        return ontologyRepository.checkHasCondition(memberId);
    }

    public void inputMemberCondition(ConditionReqDto conditionReqDto) {
        ontologyRepository.inputMemberCondition(conditionReqDto);
    }

    public List<FlyScheduleResDto> getMemberSchedule(Long memberId) {
        return ontologyRepository.getMemberSchedule(memberId);
    }

    // TODO: 비즈니스 로직 구현.


}
