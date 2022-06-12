package com.nhnacademy.minidoorayprojectmanagementapi.milestone.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Data;

@Data
public class MilestoneModifyRequest {
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;

    private Boolean status;
}
