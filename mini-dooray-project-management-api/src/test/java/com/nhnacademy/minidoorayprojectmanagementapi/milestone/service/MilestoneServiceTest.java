package com.nhnacademy.minidoorayprojectmanagementapi.milestone.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.milestone.dto.MilestoneBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.dto.MilestoneCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.dto.MilestoneModifyRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.repository.MilestoneRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MilestoneServiceTest {
    @Autowired
    MilestoneService milestoneService;

    @Test
    void createMilestoneTest() {
        MilestoneCreationRequest creationRequest = new MilestoneCreationRequest();
        creationRequest.setName("goal 1");
        creationRequest.setStart(LocalDate.now());
        creationRequest.setEnd(LocalDate.now().plusMonths(1));

        MilestoneBasicDto result = milestoneService.createMilestone(1000L, creationRequest);

        assertThat(result.getName()).isEqualTo(creationRequest.getName());
        assertThat(result.getStart()).isEqualTo(creationRequest.getStart());
        assertThat(result.getEnd()).isEqualTo(creationRequest.getEnd());
    }

    @Test
    void milestoneModifyTest() {
        MilestoneModifyRequest modifyRequest = new MilestoneModifyRequest();
        modifyRequest.setStart(LocalDate.now());
        modifyRequest.setEnd(LocalDate.now().plusMonths(1));
        modifyRequest.setName("name");
        modifyRequest.setStatus(false);

        MilestoneBasicDto result = milestoneService.modifyMilestone(1000L, modifyRequest);
        assertThat(result.getName()).isEqualTo(modifyRequest.getName());
        assertThat(result.getStart()).isEqualTo(modifyRequest.getStart());
        assertThat(result.getEnd()).isEqualTo(modifyRequest.getEnd());
        assertThat(result.getEndStatus()).isEqualTo(modifyRequest.getStatus());
    }

    @Test
    void milestoneDeleteTest() {
        MilestoneBasicDto result = milestoneService.removeMilestone(1000L);
        assertThat(result.getMilestoneNo()).isEqualTo(1000L);
    }
}
