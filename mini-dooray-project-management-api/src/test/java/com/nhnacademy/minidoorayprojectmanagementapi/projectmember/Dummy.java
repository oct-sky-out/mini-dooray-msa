package com.nhnacademy.minidoorayprojectmanagementapi.projectmember;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.ProjectStatus;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.dto.projectmember.ProjectMemberDto;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import java.time.LocalDateTime;

public class Dummy {
    private Dummy() {}

    public static ProjectMember getTestDummyProjectAdmin(ProjectMemberDto projectMemberDto) {
        ProjectMember.Pk pk = new ProjectMember.Pk(projectMemberDto.getUserNo(),
            projectMemberDto.getProjectNo());

        return ProjectMember.builder()
            .pk(pk)
            .id(projectMemberDto.getUserId())
            .build();
    }

    public static Project getTestDummyProject(ProjectMember projectAdmin) {
        return Project.builder()
            .name("project1")
            .status(ProjectStatus.ACTIVE)
            .createdAt(LocalDateTime.now())
            .build();
    }
}
