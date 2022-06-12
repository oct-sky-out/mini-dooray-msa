package com.nhnacademy.minidoorayprojectmanagementapi.task.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskRegisterMilestoneRequest {
    @NotNull
    private Long milestoneNo;
}
