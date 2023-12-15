package com.fievgo.server.dto;

import lombok.Data;

@Data
public class LoginReqDto {
    private String email;
    private String password;
}
