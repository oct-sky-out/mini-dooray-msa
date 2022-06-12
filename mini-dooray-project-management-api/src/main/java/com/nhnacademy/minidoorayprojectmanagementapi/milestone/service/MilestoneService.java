package com.nhnacademy.minidoorayprojectmanagementapi.milestone.service;

import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.ProjectNotFoundException;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.dto.MilestoneBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.dto.MilestoneCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.entity.Milestone;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.repository.MilestoneRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;

    public MilestoneService(MilestoneRepository milestoneRepository,
                            ProjectRepository projectRepository) {
        this.milestoneRepository = milestoneRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public MilestoneBasicDto createMilestone(Long projectNo, MilestoneCreationRequest creationRequest) {
        Project project = projectRepository.findById(projectNo)
            .orElseThrow(ProjectNotFoundException::new);

        Milestone milestone = Milestone.builder()
            .name(creationRequest.getName())
            .start(creationRequest.getStart())
            .end(creationRequest.getEnd())
            .endStatus(false)
            .project(project)
            .build();

        Milestone savedMilestone = milestoneRepository.saveAndFlush(milestone);

        return MilestoneBasicDto.builder()
            .milestoneNo(savedMilestone.getMilestoneNo())
            .name(savedMilestone.getName())
            .start(savedMilestone.getStart())
            .end(savedMilestone.getEnd())
            .endStatus(savedMilestone.getEndStatus())
            .build()
            ;
    }
}
