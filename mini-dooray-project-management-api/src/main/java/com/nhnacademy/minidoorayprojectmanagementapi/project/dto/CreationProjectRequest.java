package com.nhnacademy.minidoorayprojectmanagementapi.project.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreationProjectRequest {
    @NotNull
    Long userNo;

    @NotBlank
    @Length(max = 50)
    String userId;

    @NotBlank
    @Length(max = 30)
    String projectName;
}
