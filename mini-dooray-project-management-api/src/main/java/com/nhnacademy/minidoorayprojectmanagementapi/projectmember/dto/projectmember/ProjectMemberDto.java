package com.nhnacademy.minidoorayprojectmanagementapi.projectmember.dto.projectmember;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectMemberDto {
    @NotNull
    private Long userNo;

    @NotNull
    private Long projectNo;

    @NotBlank
    private String userId;
}
