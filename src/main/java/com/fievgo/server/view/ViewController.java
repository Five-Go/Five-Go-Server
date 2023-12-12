package com.fievgo.server.view;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ViewController {
    private final OntologyService ontologyService;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        log.info("get Login Page");
        model.addAttribute("loginReqDto", new LoginReqDto());
        return "login";
    }

    @PostMapping("/login")
    public String postLoginRequest(@ModelAttribute("model") LoginReqDto model, RedirectAttributes redirectAttributes) {
        Long memberId = ontologyService.getMemberId(model);
        log.info("검증로직 및 리다이렉트");
        redirectAttributes.addAttribute("member", memberId);

        boolean hasCondition = ontologyService.hasCondition(memberId);

        if (hasCondition) {
            return "redirect:main";
        } else {
            return "redirect:conditionInput";
        }
    }

    @GetMapping("/conditionInput")
    public String getConditionInputPage(Model model, @ModelAttribute("member") Long memberId) {
        log.info("컨디션 입력 페이지");
        ConditionReqDto conditionReqDto = new ConditionReqDto();
        conditionReqDto.setMemberId(memberId);
        model.addAttribute("conditionReqDto", conditionReqDto);

        return "conditionInput_button_version";
    }

    @PostMapping("/conditionInput")
    public String postConditionInputPage(@ModelAttribute("model") ConditionReqDto conditionReqDto,
                                         RedirectAttributes redirectAttributes) {
        log.info("컨디션 입력");
        log.info("Condition: {}", conditionReqDto.getCondition());
        log.info("id: {}", conditionReqDto.getMemberId());
        ontologyService.inputMemberCondition(conditionReqDto);
        redirectAttributes.addFlashAttribute("conditionReqDto", conditionReqDto);
        return "redirect:main";
    }

    @GetMapping("/main")
    public String getMainPage(Model model, @ModelAttribute("conditionReqDto") ConditionReqDto conditionReqDto) {
        log.info("메인 페이");
        Long memberId = conditionReqDto.getMemberId();
        List<FlyScheduleResDto> memberSchedules = ontologyService.getMemberSchedule(memberId);
        model.addAttribute("memberSchedules", memberSchedules);
        return "main";
    }

    @GetMapping("/details/{schedule}")
    public String getFlyScheduleDetailPage(Model model, @PathVariable("schedule") String schedule) {
        log.info("디테일 페이지");
        //TODO : 데테이페이지 구현
        return "details";
    }

}
