package com.nhnacademy.minidoorayprojectmanagementapi.milestone.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.milestone.entity.Milestone;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MilestoneRepositoryTest {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    MilestoneRepository milestoneRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void saveMilestoneTest() {
        Project project = projectRepository.findById(1000L).get();

        Milestone milestone = Milestone.builder()
            .project(project)
            .name("목표 1단계")
            .start(LocalDate.now())
            .end(LocalDate.now().plusDays(3))
            .endStatus(false)
            .build();

        Milestone savedMilestone = milestoneRepository.saveAndFlush(milestone);
        assertThat(savedMilestone.getName()).isEqualTo(milestone.getName());
        assertThat(savedMilestone.getStart()).isEqualTo(milestone.getStart());
        assertThat(savedMilestone.getEnd()).isEqualTo(milestone.getEnd());
        assertThat(savedMilestone.getEndStatus()).isEqualTo(milestone.getEndStatus());
    }

    @Test
    void modifyMilestoneTest() {
        Milestone milestone = milestoneRepository.findById(1000L).get();
        Milestone modifyMilestone = Milestone.builder()
            .milestoneNo(milestone.getMilestoneNo())
            .project(milestone.getProject())
            .name(milestone.getName())
            .end(LocalDate.now().plusMonths(1))
            .start(LocalDate.now())
            .endStatus(false)
            .build();

        Milestone modified = milestoneRepository.saveAndFlush(modifyMilestone);

        assertThat(modified).isEqualTo(modifyMilestone);
    }
}
