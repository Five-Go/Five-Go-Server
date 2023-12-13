package com.fievgo.server.service;

import static com.fievgo.server.utils.ErrorMessage.MEMBER_NOT_FOUND_ERROR;

import com.fievgo.server.domain.Member;
import com.fievgo.server.dto.FlyScheduleResDto;
import com.fievgo.server.dto.LoginReqDto;
import com.fievgo.server.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getMember(LoginReqDto model) {
        log.info("Member email : {}, Member pwd : {}", model.getEmail(), model.getPassword());
        Member byEmail = memberRepository.findByEmail(model.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 이메일입니다."));

        log.info("Search ->  Member email : {}, Member pwd : {}", byEmail.getEmail(), byEmail.getPassword());
        if (!(byEmail.getPassword().equals(model.getPassword()))) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return byEmail;
    }

    public List<FlyScheduleResDto> convertMemberName(List<FlyScheduleResDto> schedules) {
        return schedules.stream()
                .map(flyScheduleResDto -> {
                    flyScheduleResDto.changeCaptainName(getMemberNameByDB(flyScheduleResDto.getCaptain()));
                    flyScheduleResDto.changeFirstOfficerName(getMemberNameByDB(flyScheduleResDto.getFirstOfficer()));
                    flyScheduleResDto.changeMechanicName(getMemberNameByDB(flyScheduleResDto.getMechanic()));
                    return flyScheduleResDto;
                }).toList();
    }

    private String getMemberNameByDB(String memberName) {
        Long memberId = Long.parseLong(memberName.split("n")[1]);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(MEMBER_NOT_FOUND_ERROR.getMessage()));
        return member.getNameKr();
    }
}
