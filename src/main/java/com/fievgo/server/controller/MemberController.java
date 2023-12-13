package com.fievgo.server.controller;

import com.fievgo.server.dto.ConditionReqDto;
import com.fievgo.server.service.OntologyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final OntologyService ontologyService;

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
}
