package com.fievgo.server.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FactorAndCondtionDto {
    private Long condition;
    private List<String> factors;

    public static FactorAndCondtionDto of(Long condition, List<String> factors) {
        return FactorAndCondtionDto.builder()
                .condition(condition)
                .factors(factors)
                .build();
    }
}
