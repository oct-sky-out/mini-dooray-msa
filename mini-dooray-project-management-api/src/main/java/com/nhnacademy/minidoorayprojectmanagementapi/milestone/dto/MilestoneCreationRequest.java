package com.nhnacademy.minidoorayprojectmanagementapi.milestone.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class MilestoneCreationRequest {
    @NotBlank
    @Length(max = 20)
    String name;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate start;

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate end;
}
