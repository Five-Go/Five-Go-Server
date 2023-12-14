package com.fievgo.server.controller;

import com.fievgo.server.auth.SessionConst;
import com.fievgo.server.domain.Member;
import com.fievgo.server.dto.LoginReqDto;
import com.fievgo.server.service.MemberService;
import com.fievgo.server.service.OntologyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;
    private final OntologyService ontologyService;

    @PostMapping("/login")
    public String postLoginRequest(@ModelAttribute("model") LoginReqDto model, HttpServletRequest request) {
        Member loginMember = memberService.getMember(model);
        log.info("검증로직 및 리다이렉트");
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        boolean hasCondition = ontologyService.hasCondition(loginMember.getId());
        log.info("hasCondition {}", hasCondition);
        if (hasCondition) {
            return "redirect:main";
        } else {
            return "redirect:conditionInput";
        }
    }
}
