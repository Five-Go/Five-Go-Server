package com.fievgo.server.service;

import static com.fievgo.server.common.ErrorMessage.MEMBER_NOT_FOUND_ERROR;

import com.fievgo.server.domain.Member;
import com.fievgo.server.dto.FlyScheduleResDto;
import com.fievgo.server.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

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
