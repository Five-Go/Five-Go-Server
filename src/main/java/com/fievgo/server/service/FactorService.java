package com.fievgo.server.service;

import com.fievgo.server.domain.Factor;
import com.fievgo.server.dto.FactorWeightResDto;
import com.fievgo.server.repository.FactorRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FactorService {
    private final FactorRepository factorRepository;

    public List<FactorWeightResDto> changeFactorName(List<FactorWeightResDto> topDangerFactor) {
        List<Factor> allFactors = factorRepository.findAll();
        Map<Long, Factor> factorIdToNameMap = allFactors.stream()
                .collect(Collectors.toMap(Factor::getId, factor -> factor));

        topDangerFactor
                .forEach(dto -> {
                    dto.setDbFactor(factorIdToNameMap.get(Long.parseLong(dto.getFactor())));
                });

        return topDangerFactor;
    }
}
