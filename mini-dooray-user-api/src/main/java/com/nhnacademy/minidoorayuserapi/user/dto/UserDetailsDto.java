package com.nhnacademy.minidoorayuserapi.user.dto;

import lombok.Data;

@Data
public class UserDetailsDto {
    private Long userNo;

    private String id;

    private String password;

    private String email;

    private String status;
}
