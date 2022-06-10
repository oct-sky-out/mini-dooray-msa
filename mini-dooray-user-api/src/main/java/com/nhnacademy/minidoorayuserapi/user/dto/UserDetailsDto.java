package com.nhnacademy.minidoorayuserapi.user.dto;

import com.nhnacademy.minidoorayuserapi.user.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserDetailsDto {
    private Long userNo;

    private String id;

    private String password;

    private String email;

    private UserStatus status;
}
