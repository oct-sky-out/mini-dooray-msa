package com.nhnacademy.minidoorayprojectmanagementapi.milestone.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.milestone.dto.MilestoneBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.dto.MilestoneCreationRequest;
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
    void createMilestone() {
        MilestoneCreationRequest creationRequest = new MilestoneCreationRequest();
        creationRequest.setName("goal 1");
        creationRequest.setStart(LocalDate.now());
        creationRequest.setEnd(LocalDate.now().plusMonths(1));

        MilestoneBasicDto result = milestoneService.createMilestone(1000L, creationRequest);

        assertThat(result.getName()).isEqualTo(creationRequest.getName());
        assertThat(result.getStart()).isEqualTo(creationRequest.getStart());
        assertThat(result.getEnd()).isEqualTo(creationRequest.getEnd());
    }
}
