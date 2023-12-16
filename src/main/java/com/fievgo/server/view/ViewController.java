package com.fievgo.server.view;

import com.fievgo.server.auth.Login;
import com.fievgo.server.domain.Member;
import com.fievgo.server.dto.ConditionReqDto;
import com.fievgo.server.dto.FactorFinalOrganizeResDto;
import com.fievgo.server.dto.FactorWeightResDto;
import com.fievgo.server.dto.FlyScheduleResDto;
import com.fievgo.server.dto.LoginReqDto;
import com.fievgo.server.dto.WeatherConditionDto;
import com.fievgo.server.service.FactorService;
import com.fievgo.server.service.OntologyService;
import com.fievgo.server.service.WeatherService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
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
    private final WeatherService weatherService;
    private final FactorService factorService;

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
        WeatherConditionDto startAPWeather = weatherService.getAirPortWeather(scheduleDto.getStartAirport());
        WeatherConditionDto endAPWeather = weatherService.getAirPortWeather(scheduleDto.getEndAirport());
        List<FactorWeightResDto> topDangerFactor = ontologyService.getTopDangerFactor(schedule);
        List<FactorWeightResDto> factorWeightResDtos = factorService.changeFactorName(topDangerFactor);

        model.addAttribute("schedule", scheduleDto);
        model.addAttribute("condition", ontologyService.getConditionBySchedule(scheduleDto.getSchedule()));
        model.addAttribute("member", loginMember);
        model.addAttribute("startAPWeather", startAPWeather);
        model.addAttribute("endAPWeather", endAPWeather);
        model.addAttribute("factors", factorWeightResDtos);
        model.addAttribute("organize",
                FactorFinalOrganizeResDto.of(factorWeightResDtos.get(0), getMostResult(factorWeightResDtos)));
        return "상세일정페이지_최종";
    }

    private String getMostResult(List<FactorWeightResDto> factorWeightResDtos) {
        HashMap<String, Integer> count = new HashMap<>();
        for (FactorWeightResDto factorWeightResDto : factorWeightResDtos) {
            String result = factorWeightResDto.getResult();
            if (count.containsKey(result)) {
                count.put(result, count.get(result) + 1);
            } else {
                count.put(result, 1);
            }
        }

        Optional<Entry<String, Integer>> entryWithMaxValue = count.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        // 가장 큰 값의 key 반환 (없을 경우 null)
        return entryWithMaxValue.map(Entry::getKey).orElse(null);
    }

}
