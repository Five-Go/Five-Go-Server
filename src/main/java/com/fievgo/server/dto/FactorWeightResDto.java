package com.fievgo.server.dto;

import com.fievgo.server.domain.Factor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FactorWeightResDto {
    private String factor;
    private Long weight;
    private String name;
    private String result;

    public static FactorWeightResDto of(String factor, Long weight) {
        return FactorWeightResDto.builder()
                .factor(factor)
                .weight(weight)
                .build();
    }

    public void setDbFactor(Factor factor) {
        this.name = factor.getName();
        this.result = factor.getResult();
    }
}
