package com.nhnacademy.minidooraygateway.project.respone;

import com.nhnacademy.minidooraygateway.project.dto.ProjectExecutionCompleteDto;
import com.nhnacademy.minidooraygateway.projectmember.dto.ProjectMemberDto;
import lombok.Data;

@Data
public class ProjectCreationSuccessResponse {
    private ProjectExecutionCompleteDto project;

    private ProjectMemberDto admin;
}
