package com.nhnacademy.minidoorayprojectmanagementapi.milestone.controller;

import com.nhnacademy.minidoorayprojectmanagementapi.milestone.dto.MilestoneBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.dto.MilestoneCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.service.MilestoneService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects/{projectNo}/milestone")
public class MilestoneController {
    private final MilestoneService milestoneService;

    public MilestoneController(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MilestoneBasicDto generateMilestone(@PathVariable("projectNo") Long projectNo,
                                               @Validated @RequestBody
                                                MilestoneCreationRequest creationRequest){
        return milestoneService.createMilestone(projectNo, creationRequest);
    }

}
