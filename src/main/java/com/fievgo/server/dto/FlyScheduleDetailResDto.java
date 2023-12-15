package com.fievgo.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlyScheduleDetailResDto {
    private final String factor;
    private final String result;
    private final String cause;
    private final Integer danger;
}
