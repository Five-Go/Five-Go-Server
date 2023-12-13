package com.fievgo.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConditionReqDto {
    private Long memberId;
    private Long condition;

    public static ConditionReqDto of(Long memberId, Long condition) {
        return ConditionReqDto.builder()
                .memberId(memberId)
                .condition(condition)
                .build();
    }
}
