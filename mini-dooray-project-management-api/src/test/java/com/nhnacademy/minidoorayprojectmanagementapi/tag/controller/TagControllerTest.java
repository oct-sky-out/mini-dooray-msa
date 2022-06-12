package com.nhnacademy.minidoorayprojectmanagementapi.tag.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidoorayprojectmanagementapi.tag.dto.TagBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.tag.dto.TagCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.tag.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TagController.class)
class TagControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TagService tagService;

    @Test
    void generateTag() throws Exception {
        TagBasicDto tagBasicDto = new TagBasicDto();
        tagBasicDto.setTagNo(100L);
        tagBasicDto.setName("hello");
        tagBasicDto.setProject(null);

        given(tagService.createTag(8L, "hello"))
            .willReturn(tagBasicDto);

        TagCreationRequest tagCreationRequest = new TagCreationRequest();
        tagCreationRequest.setTagName("hello");

        String json = new ObjectMapper().writeValueAsString(tagCreationRequest);
        mockMvc.perform(post("/projects/{projectNo}/tags", 8L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.tagNo")
                .value(equalTo(100)))
            .andExpect(jsonPath("$.name")
                .value(equalTo("hello")))
            .andExpect(jsonPath("$.project")
                .value(equalTo(null)));
    }
}
