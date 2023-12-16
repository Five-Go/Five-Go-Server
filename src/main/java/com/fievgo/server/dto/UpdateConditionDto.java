package com.fievgo.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateConditionDto {
    private Long condition;
    private String conditionInputPerson;

    public static UpdateConditionDto of(String id, Long condition) {
        String prefix = "http://www.semanticweb.org/fivego#";
        return UpdateConditionDto.builder()
                .condition(condition)
                .conditionInputPerson(prefix + id)
                .build();
    }
}
