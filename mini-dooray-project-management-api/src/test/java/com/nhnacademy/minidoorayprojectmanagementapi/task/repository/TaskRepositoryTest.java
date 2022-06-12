package com.nhnacademy.minidoorayprojectmanagementapi.task.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository.ProjectMemberRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskDetailResponse;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskModifyRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskPageDto;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskRepositoryTest {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectMemberRepository projectMemberRepository;

    @Test
    void saveTaskTest() {
        Project project = projectRepository.findById(1000L).get();
        ProjectMember member = projectMemberRepository.findById(new ProjectMember.Pk(100L,1000L)).get();

        Task testTask = Task.builder()
            .taskNo(1L)
            .content("content")
            .createdAt(LocalDateTime.now())
            .project(project)
            .author(member.getPk().getUserNo())
            .title("title")
            .build();

        Task saved = taskRepository.saveAndFlush(testTask);

        assertThat(saved.getContent()).isEqualTo(testTask.getContent());
        assertThat(saved.getCreatedAt()).isEqualTo(testTask.getCreatedAt());
        assertThat(saved.getProject()).isEqualTo(testTask.getProject());
        assertThat(saved.getAuthor()).isEqualTo(testTask.getAuthor());
        assertThat(saved.getTitle()).isEqualTo(testTask.getTitle());
    }

    @Test
    void modifyTaskTest() {
        TaskModifyRequest taskModifyRequest = new TaskModifyRequest();
        taskModifyRequest.setTaskNo(8L);
        taskModifyRequest.setTitle("midifyTitle");
        taskModifyRequest.setContent("modify content");

        Task task = taskRepository.findById(taskModifyRequest.getTaskNo()).get();

        task.modifyTask(taskModifyRequest.getTitle(), taskModifyRequest.getContent());

        assertThat(taskRepository.saveAndFlush(task))
            .isEqualTo(task);
    }

    @Test
    void findAllTasks() {
        Long projectNo = 1000L;
        Pageable pageable = PageRequest.of(0, 5);
        Page<TaskPageDto> taskPageDtos = taskRepository.findAllTaskByPageable(projectNo, pageable);

        assertThat(taskPageDtos.hasNext()).isFalse();
        assertThat(taskPageDtos.hasContent()).isTrue();

        TaskPageDto task = taskPageDtos.getContent().stream()
            .findFirst()
            .get();

        assertThat(task.getTitle()).isEqualTo("tit");
    }

    @Test
    void findTaskByTestIdTest() {
        TaskDetailResponse findTask = taskRepository.findTaskDetail(1000L, 9L).get();

        assertThat(findTask.getTaskNo()).isEqualTo(9L);
        assertThat(findTask.getProject().getProjectNo()).isEqualTo(1000L);
        assertThat(findTask.getMilestone().getMilestoneNo()).isEqualTo(null);
        assertThat(findTask.getAuthor()).isEqualTo("anonymous3");
    }
}
