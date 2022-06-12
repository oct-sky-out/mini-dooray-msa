package com.nhnacademy.minidoorayprojectmanagementapi.tag.controller;

import com.nhnacademy.minidoorayprojectmanagementapi.tag.dto.TagBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.tag.dto.TagCreationRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.tag.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects/{projectNo}/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagBasicDto generateTag(@PathVariable("projectNo") Long projectNo,
                                   @Validated @RequestBody TagCreationRequest tagCreationRequest){
        return tagService.createTag(projectNo,tagCreationRequest.getTagName());
    }
}
