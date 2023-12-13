package com.fievgo.server.view;

import com.fievgo.server.auth.Login;
import com.fievgo.server.domain.Member;
import com.fievgo.server.dto.ConditionReqDto;
import com.fievgo.server.dto.FlyScheduleResDto;
import com.fievgo.server.dto.LoginReqDto;
import com.fievgo.server.service.OntologyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ViewController {
    private final OntologyService ontologyService;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginReqDto", new LoginReqDto());
        return "login";
    }

    @GetMapping("/conditionInput")
    public String getConditionInputPage(Model model, @Login Member member) {
        ConditionReqDto conditionReqDto = new ConditionReqDto();
        conditionReqDto.setMemberId(member.getId());
        model.addAttribute("conditionReqDto", conditionReqDto);

        return "conditionInput_button_version";
    }

    @GetMapping("/main")
    public String getMainPage(Model model, @ModelAttribute("conditionReqDto") ConditionReqDto conditionReqDto,
                              @Login Member member) {
        Long memberId = member.getId();
        List<FlyScheduleResDto> memberSchedules = ontologyService.getMemberSchedules(memberId);
        model.addAttribute("memberSchedules", memberSchedules);
        return "main";
    }

    @GetMapping("/details/{schedule}")
    public String getFlyScheduleDetailPage(Model model, @PathVariable("schedule") String schedule,
                                           @Login Member loginMember) {
        FlyScheduleResDto scheduleDto = ontologyService.getSchedule(schedule);
        model.addAttribute("schedule", scheduleDto);
        model.addAttribute("condition", ontologyService.getConditionBySchedule(scheduleDto.getSchedule()));
        model.addAttribute("member", loginMember);
        return "상세일정페이지";
    }

}
