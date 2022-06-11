package com.nhnacademy.minidoorayprojectmanagementapi.projectmember.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.dto.ProjectMemberDto;
import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.service.ProjectMemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProjectMemberController.class)
class ProjectMemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjectMemberService projectMemberService;

    @Test
    void addProjectMember() throws Exception {
        ProjectMemberDto projectMemberDto = new ProjectMemberDto();
        projectMemberDto.setProjectNo(1L);
        projectMemberDto.setUserNo(2L);
        projectMemberDto.setUserId("user2");

        String requestBody = new ObjectMapper().writeValueAsString(projectMemberDto);

        given(projectMemberService.addProjectMember(projectMemberDto))
            .willReturn(projectMemberDto);

        mockMvc.perform(post("/projects/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.userNo")
                .value(equalTo(projectMemberDto.getUserNo().intValue())))
            .andExpect(jsonPath("$.projectNo")
                .value(equalTo(projectMemberDto.getProjectNo().intValue())))
            .andExpect(jsonPath("$.userId")
                .value(equalTo(projectMemberDto.getUserId())));
    }
}
