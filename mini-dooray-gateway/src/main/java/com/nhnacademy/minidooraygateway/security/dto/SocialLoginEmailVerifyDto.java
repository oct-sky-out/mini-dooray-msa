package com.nhnacademy.minidooraygateway.security.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SocialLoginEmailVerifyDto {
    @NotBlank
    @Length(min = 1)
    @Email
    private String email;
}
