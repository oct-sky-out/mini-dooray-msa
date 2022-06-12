package com.nhnacademy.minidoorayprojectmanagementapi.task.service;

import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.TaskCreateException;
import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.TaskNotFoundException;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.entity.Milestone;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository.ProjectMemberRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskDetailResponse;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskExecutionCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskModifyRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskPageDto;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskPageResponse;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import com.nhnacademy.minidoorayprojectmanagementapi.task.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository,
                       ProjectMemberRepository projectMemberRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    @Transactional
    public TaskExecutionCompleteDto createTask(TaskCreationRequest creationRequest) {
        Optional<Project> project = projectRepository.findById(creationRequest.getProjectNo());
        Optional<ProjectMember> member = projectMemberRepository.findById(
            new ProjectMember.Pk(creationRequest.getAuthorNo(), creationRequest.getProjectNo()));

        if (project.isPresent() && member.isPresent()) {
            Task task = Task.builder()
                .title(creationRequest.getTitle())
                .content(creationRequest.getContent())
                .project(project.get())
                .author(member.get().getPk().getUserNo())
                .createdAt(LocalDateTime.now())
                .build();
            Task savedTask = taskRepository.saveAndFlush(task);
            return TaskExecutionCompleteDto.builder()
                .projectNo(savedTask.getProject().getProjectNo())
                .author(savedTask.getAuthor())
                .title(savedTask.getTitle())
                .content(savedTask.getContent())
                .createdAt(savedTask.getCreatedAt())
                .build();
        }
        throw new TaskCreateException();
    }

    @Transactional
    public TaskExecutionCompleteDto modifyTask(TaskModifyRequest taskModifyRequest) {
        Task task = taskRepository.findById(taskModifyRequest.getTaskNo())
            .orElseThrow(TaskNotFoundException::new);

        task.modifyTask(taskModifyRequest.getTitle(), taskModifyRequest.getContent());

        Long milestoneNo = getMilestoneNo(task);

        taskRepository.saveAndFlush(task);
        return TaskExecutionCompleteDto.builder()
            .taskNo(task.getTaskNo())
            .projectNo(task.getProject().getProjectNo())
            .title(task.getTitle())
            .content(task.getContent())
            .milestoneNo(milestoneNo)
            .author(task.getAuthor())
            .createdAt(task.getCreatedAt())
            .build();
    }

    @Transactional
    public TaskExecutionCompleteDto removeTask(Long taskNo) {
        Task task = taskRepository.findById(taskNo)
            .orElseThrow(TaskNotFoundException::new);

        taskRepository.delete(task);
        return TaskExecutionCompleteDto.builder()
            .taskNo(task.getTaskNo())
            .projectNo(task.getProject().getProjectNo())
            .title(task.getTitle())
            .content(task.getContent())
            .milestoneNo(getMilestoneNo(task))
            .author(task.getAuthor())
            .createdAt(task.getCreatedAt())
            .build();
    }

    private Long getMilestoneNo(Task task) {
        Optional<Milestone> mileStone = Optional.ofNullable(task.getMileStone());
        return mileStone.map(Milestone::getMilestoneNo).orElse(null);
    }

    public TaskPageResponse getTaskPage(Long projectNo, Pageable pageable) {
        Page<TaskPageDto> taskPage = taskRepository.findAllTaskByPageable(projectNo, pageable);

        return new TaskPageResponse(
            taskPage.getContent(),
            taskPage.hasNext(),
            taskPage.hasPrevious(),
            taskPage.getNumber()
        );
    }

    public TaskDetailResponse findDetailTask(Long projectNo, Long taskNo) {
        return taskRepository.findTaskDetail(projectNo, taskNo)
            .orElseThrow(TaskNotFoundException::new);
    }

    public TaskExecutionCompleteDto registerMilestoneToTask(Long projectNo, Long taskNo, Long milestoneNo) {
        taskRepository.registerMilestone(projectNo, taskNo, milestoneNo);

        Task task = taskRepository.findById(taskNo)
            .orElseThrow(TaskNotFoundException::new);

        return TaskExecutionCompleteDto.builder()
            .taskNo(task.getTaskNo())
            .projectNo(task.getProject().getProjectNo())
            .title(task.getTitle())
            .content(task.getContent())
            .milestoneNo(getMilestoneNo(task))
            .author(task.getAuthor())
            .createdAt(task.getCreatedAt())
            .build();
    }
}
