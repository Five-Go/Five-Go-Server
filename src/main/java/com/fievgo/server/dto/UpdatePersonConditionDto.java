package com.fievgo.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePersonConditionDto {
    private Long condition;
    private String conditionInputPerson;

    public static UpdatePersonConditionDto of(Long id, Long condition) {
        String prefix = "http://www.semanticweb.org/fivego#Person";
        return UpdatePersonConditionDto.builder()
                .condition(condition)
                .conditionInputPerson(prefix + id)
                .build();
    }
}
