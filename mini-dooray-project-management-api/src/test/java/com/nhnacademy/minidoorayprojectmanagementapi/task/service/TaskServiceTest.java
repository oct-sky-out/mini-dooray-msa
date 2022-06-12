package com.nhnacademy.minidoorayprojectmanagementapi.task.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.ProjectStatus;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository.ProjectMemberRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskDetailResponse;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskExecutionCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskModifyRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskPageResponse;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import com.nhnacademy.minidoorayprojectmanagementapi.task.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Test
    void taskModifyServiceTest() {
        TaskModifyRequest modifyRequest = new TaskModifyRequest();
        modifyRequest.setTaskNo(8L);
        modifyRequest.setTitle("title123");
        modifyRequest.setContent("hello");

        TaskExecutionCompleteDto executionCompleteDto = taskService.modifyTask(modifyRequest);
        assertThat(executionCompleteDto.getContent()).isEqualTo(modifyRequest.getContent());
        assertThat(executionCompleteDto.getTitle()).isEqualTo(modifyRequest.getTitle());
        assertThat(executionCompleteDto.getTaskNo()).isEqualTo(modifyRequest.getTaskNo());
    }

    @Test
    void taskDeleteTest() {
        Long taskNo = 8L;
        TaskExecutionCompleteDto executionCompleteDto = taskService.removeTask(taskNo);

        assertThat(executionCompleteDto.getTaskNo())
            .isEqualTo(taskNo);
    }

    @Test
    void getTaskPageTest() {
        Long projectNo = 1000L;
        Pageable pageable = PageRequest.of(0, 5);

        TaskPageResponse responseBody = taskService.getTaskPage(projectNo, pageable);
        assertThat(responseBody.getContent()).hasSize(2);
        assertThat(responseBody.getCurrentPage()).isEqualTo(0);
        assertThat(responseBody.getHasNextPage()).isFalse();
        assertThat(responseBody.getHasPreviousPage()).isFalse();
    }

    @Test
    void findDetailTaskTest() {
        Long projectNo = 1000L;
        Long taskNo = 8L;
        TaskDetailResponse taskDetailResponse =
            taskService.findDetailTask(projectNo, taskNo);

        assertThat(taskDetailResponse.getTaskNo())
            .isEqualTo(taskNo);
        assertThat(taskDetailResponse.getProject().getProjectNo())
            .isEqualTo(projectNo);
        assertThat(taskDetailResponse.getAuthor())
            .isEqualTo("anonymous3");
        assertThat(taskDetailResponse.getMilestone().getMilestoneNo())
            .isNull();
    }

    @Test
    void registerMilestoneToTaskTest() {
        Long projectNo = 1000L;
        Long taskNo = 8L;
        Long milestoneNo = 1000L;
        TaskExecutionCompleteDto result =
            taskService.registerMilestoneToTask(projectNo, taskNo, milestoneNo);

        assertThat(result.getTaskNo()).isEqualTo(taskNo);
        assertThat(result.getProjectNo()).isEqualTo(projectNo);
        assertThat(result.getMilestoneNo()).isEqualTo(milestoneNo);
    }
}
