package com.nhnacademy.minidooraygateway.user.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class SignUpRequest {
    private String id;
    private String password;
    private String email;

    public void changeHashedPassword(String hashedPassword){
        this.password = hashedPassword;
    }
}
