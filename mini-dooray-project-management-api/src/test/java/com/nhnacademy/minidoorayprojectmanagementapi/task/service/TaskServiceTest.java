package com.nhnacademy.minidoorayprojectmanagementapi.task.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.ProjectStatus;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository.ProjectMemberRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskExecutionCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import com.nhnacademy.minidoorayprojectmanagementapi.task.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class TaskServiceTest {
    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @MockBean
    ProjectRepository projectRepository;

    @MockBean
    ProjectMemberRepository projectMemberRepository;

    @Test
    void createTask() {
        TaskCreationRequest request = new TaskCreationRequest();
        request.setProjectNo(1000L);
        request.setAuthorNo(1L);
        request.setTitle("title");
        request.setContent("contnet");

        Project project = Project.builder().
            name("proj")
            .projectNo(1000L)
            .createdAt(LocalDateTime.now())
            .status(ProjectStatus.ACTIVE)
            .build();
        given(projectRepository.findById(request.getProjectNo()))
            .willReturn(Optional.of(project));
        ProjectMember.Pk pk = new ProjectMember.Pk(request.getAuthorNo(), request.getProjectNo());
        given(projectMemberRepository.findById(pk))
            .willReturn(Optional.of(
                ProjectMember.builder()
                    .pk(pk)
                    .id("id")
                    .isAdmin(true)
                    .build()
            ));

        TaskExecutionCompleteDto completeDto = taskService.createTask(request);
        assertThat(completeDto.getAuthor()).isEqualTo(request.getAuthorNo());
        assertThat(completeDto.getProjectNo()).isEqualTo(request.getProjectNo());
        assertThat(completeDto.getTitle()).isEqualTo(request.getTitle());
        assertThat(completeDto.getContent()).isEqualTo(request.getContent());
    }
}
