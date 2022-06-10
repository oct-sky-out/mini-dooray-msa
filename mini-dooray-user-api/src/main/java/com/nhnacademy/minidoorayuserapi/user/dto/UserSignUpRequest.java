package com.nhnacademy.minidoorayuserapi.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserSignUpRequest {
    @NotBlank
    @Length(max = 50)
    private String id;
    @NotBlank
    @Length(max = 100)
    private String password;
    @Email
    @NotBlank
    @Length(max = 100)
    private String email;
}
