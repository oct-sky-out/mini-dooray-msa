package com.nhnacademy.minidoorayprojectmanagementapi.milestone.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.dto.MilestoneBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.dto.MilestoneCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.dto.MilestoneModifyRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.entity.Milestone;
import com.nhnacademy.minidoorayprojectmanagementapi.milestone.service.MilestoneService;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.ProjectStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MilestoneController.class)
class MilestoneControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MilestoneService milestoneService;

    @Test
    void generateMilestoneTest() throws Exception {
        Project project = Project.builder()
            .projectNo(1L)
            .name("proj")
            .createdAt(LocalDateTime.now())
            .status(ProjectStatus.ACTIVE)
            .build();

        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(30);
        MilestoneCreationRequest request = new MilestoneCreationRequest();
        request.setName("goal 1");
        request.setStart(start);
        request.setEnd(end);

        MilestoneBasicDto milestone = MilestoneBasicDto.builder()
            .milestoneNo(1L)
            .name("goal 1")
            .start(start)
            .end(end)
            .endStatus(false)
            .build();

        given(milestoneService.createMilestone(1L, request))
            .willReturn(milestone);

        String json = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .writeValueAsString(request);

        mockMvc.perform(post("/projects/{projectNo}/milestone", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.milestoneNo")
                .value(equalTo(milestone.getMilestoneNo().intValue())))
            .andExpect(jsonPath("$.name")
                .value(equalTo(request.getName())))
            .andExpect(jsonPath("$.start")
                .value(equalTo(request.getStart().toString())))
            .andExpect(jsonPath("$.end")
                .value(equalTo(request.getEnd().toString())))
            .andExpect(jsonPath("$.endStatus")
                .value(equalTo(false)));
    }

    @Test
    void modifyMilestone() throws Exception {
        MilestoneBasicDto milestoneResult = MilestoneBasicDto.builder()
            .milestoneNo(1L)
            .name("modify name")
            .start(LocalDate.now())
            .end(LocalDate.now().plusMonths(1))
            .endStatus(false)
            .build();

        MilestoneModifyRequest modifyRequest = new MilestoneModifyRequest();
        modifyRequest.setStart(milestoneResult.getStart());
        modifyRequest.setEnd(milestoneResult.getEnd());
        modifyRequest.setName("modify name");
        modifyRequest.setStatus(false);

        given(milestoneService.modifyMilestone(1L, modifyRequest))
            .willReturn(milestoneResult);

        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(modifyRequest);
        mockMvc.perform(put("/projects/{projectNo}/milestone/{milestoneNo}", 1000L, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.milestoneNo")
                .value(equalTo(1)))
            .andExpect(jsonPath("$.name")
                .value(equalTo(modifyRequest.getName())));
    }
}
