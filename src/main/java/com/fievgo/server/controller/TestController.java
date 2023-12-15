package com.fievgo.server.controller;

import com.fievgo.server.dto.FactorWeightResDto;
import com.fievgo.server.service.FactorService;
import com.fievgo.server.service.OntologyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final OntologyService ontologyService;
    private final FactorService factorService;

    @GetMapping("/test")
    public List<FactorWeightResDto> test() {
        List<FactorWeightResDto> topDangerFactor = ontologyService.getTopDangerFactor("1");
        return factorService.changeFactorName(topDangerFactor);
    }
}
