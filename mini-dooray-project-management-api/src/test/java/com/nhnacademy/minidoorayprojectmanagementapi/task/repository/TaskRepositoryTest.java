package com.nhnacademy.minidoorayprojectmanagementapi.task.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository.ProjectMemberRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.task.dto.TaskModifyDto;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
        ProjectMember member = projectMemberRepository.findById(new ProjectMember.Pk(100L,8L)).get();

        Task testTask = Task.builder()
            .taskNo(1L)
            .content("content")
            .createdAt(LocalDateTime.now())
            .project(project)
            .author(member.getPk().getUserNo())
            .title("title")
            .build();

        Task saved = taskRepository.saveAndFlush(testTask);

        assertThat(saved).isEqualTo(testTask);
    }

    @Test
    void modifyTaskTest() {
        Task task = taskRepository.findById(7L).get();
        TaskModifyDto taskModifyDto = new TaskModifyDto();
        taskModifyDto.setTitle("midifyTitle");
        taskModifyDto.setContent("modify content");

        task.modifyTask(taskModifyDto.getTitle(), taskModifyDto.getContent());

        assertThat(taskRepository.saveAndFlush(task))
            .isEqualTo(task);
    }
}
