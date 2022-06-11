package com.nhnacademy.minidoorayprojectmanagementapi.project.dto;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.ProjectStatus;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectStatusModifyRequest {
    @NotNull
    Long projectNo;

    @NotNull
    ProjectStatus projectStatus;
}
