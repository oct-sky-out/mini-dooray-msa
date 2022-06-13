package com.nhnacademy.minidooraygateway.projectmember.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectMemberDto {
    @NotNull
    private Long userNo;

    @NotBlank
    private String userId;

    @NotNull
    private Long projectNo;
}
