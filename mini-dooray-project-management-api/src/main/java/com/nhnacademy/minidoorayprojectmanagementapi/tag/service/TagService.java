package com.nhnacademy.minidoorayprojectmanagementapi.tag.service;

import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.ProjectNotFoundException;
import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.TagNotFoundException;
import com.nhnacademy.minidoorayprojectmanagementapi.exceptions.TagUpdateFailureException;
import com.nhnacademy.minidoorayprojectmanagementapi.project.dto.ProjectExecutionCompleteDto;
import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.tag.dto.TagBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.tag.entity.Tag;
import com.nhnacademy.minidoorayprojectmanagementapi.tag.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;

    public TagService(TagRepository tagRepository, ProjectRepository projectRepository) {
        this.tagRepository = tagRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public TagBasicDto createTag(Long projectNumber, String tagName) {
        Project project = projectRepository.findById(projectNumber)
            .orElseThrow(ProjectNotFoundException::new);
        Tag tag = Tag.builder()
            .project(project)
            .name(tagName)
            .build();

        Tag savedTag = tagRepository.saveAndFlush(tag);
        return getTagBasicDto(tag, savedTag);
    }

    @Transactional
    public TagBasicDto modifyTagName(Long projectNo, Long tagNo, String tagName) {
        Long result = tagRepository.modifyTag(projectNo, tagNo, tagName);

        if(result != 1){
            throw new TagUpdateFailureException();
        }

        Tag modifiedTag = tagRepository.findById(tagNo)
            .orElseThrow(TagNotFoundException::new);

        return getTagBasicDto(modifiedTag, modifiedTag);
    }

    @Transactional
    public TagBasicDto deleteTag(Long tagNo) {
        Tag tag = tagRepository.findById(tagNo)
            .orElseThrow(TagNotFoundException::new);
        tagRepository.delete(tag);
        return getTagBasicDto(tag, tag);
    }

    private TagBasicDto getTagBasicDto(Tag tag, Tag modifyTag) {
        TagBasicDto tagBasicDto = new TagBasicDto();
        tagBasicDto.setTagNo(modifyTag.getTagNo());
        tagBasicDto.setName(modifyTag.getName());
        tagBasicDto.setProject(new ProjectExecutionCompleteDto(
            tag.getProject().getProjectNo(),
            tag.getProject().getName(),
            tag.getProject().getStatus(),
            tag.getProject().getCreatedAt()));
        return tagBasicDto;
    }

}
