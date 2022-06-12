package com.nhnacademy.minidoorayprojectmanagementapi.tag.controller;

import com.nhnacademy.minidoorayprojectmanagementapi.tag.dto.TagBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.tag.dto.TagRequest;
import com.nhnacademy.minidoorayprojectmanagementapi.tag.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
                                   @Validated @RequestBody TagRequest tagRequest){
        return tagService.createTag(projectNo, tagRequest.getTagName());
    }

    @PatchMapping("/{tagNo}")
    public TagBasicDto modifyTagName(@PathVariable("projectNo") Long projectNo,
                                     @PathVariable("tagNo") Long tagNo,
                                     @Validated @RequestBody TagRequest tagRequest){
        return tagService.modifyTagName(projectNo, tagNo, tagRequest.getTagName());
    }

    @DeleteMapping("/{tagNo}")
    public TagBasicDto deleteTag(@PathVariable("tagNo") Long tagNo){
        return  tagService.deleteTag(tagNo);
    }

}
