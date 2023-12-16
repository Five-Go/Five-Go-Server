package com.fievgo.server.dto;

import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirportNxNyDto {
    private Integer nx;
    private Integer ny;

    public static AirportNxNyDto of(HashMap<String, String> nxny) {
        return AirportNxNyDto.builder()
                .nx(Integer.parseInt(nxny.get("nx")))
                .ny(Integer.parseInt(nxny.get("ny")))
                .build();
    }
}
