package com.nhnacademy.minidoorayprojectmanagementapi.projectmember.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMemberDto {
    @NotNull
    private Long userNo;

    @NotBlank
    private String userId;

    @NotNull
    private Long projectNo;
}
