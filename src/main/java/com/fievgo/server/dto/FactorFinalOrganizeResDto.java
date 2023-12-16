package com.fievgo.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FactorFinalOrganizeResDto {
    private String firstFactorName;
    private String firstFactorResult;
    private String mostResult;

    public static FactorFinalOrganizeResDto of(FactorWeightResDto firstFactor, String mostResult) {
        return FactorFinalOrganizeResDto.builder()
                .firstFactorName(firstFactor.getName())
                .firstFactorResult(firstFactor.getResult())
                .mostResult(mostResult)
                .build();
    }

}
