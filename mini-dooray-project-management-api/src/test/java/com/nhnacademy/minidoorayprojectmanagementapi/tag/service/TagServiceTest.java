package com.nhnacademy.minidoorayprojectmanagementapi.tag.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.tag.dto.TagBasicDto;
import com.nhnacademy.minidoorayprojectmanagementapi.tag.dto.TagRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class TagServiceTest {
    @Autowired
    TagService tagService;

    @Test
    void createTag() {
        TagBasicDto tagBasicDto = tagService.createTag(1000L, "todo1");
        assertThat(tagBasicDto.getProject().getProjectNo()).isEqualTo(1000L);
        assertThat(tagBasicDto.getProject().getName()).isEqualTo("project100");
        assertThat(tagBasicDto.getName()).isEqualTo("todo1");
    }

    @Test
    void modifyTag() {
        TagRequest modifyTagReq = new TagRequest();
        modifyTagReq.setTagName("modify!!");

        TagBasicDto tagBasicDto = tagService.modifyTagName(1000L, 3L, modifyTagReq.getTagName());
        assertThat(tagBasicDto.getTagNo()).isEqualTo(3L);
        assertThat(tagBasicDto.getName()).isEqualTo(modifyTagReq.getTagName());
        assertThat(tagBasicDto.getProject().getProjectNo()).isEqualTo(1000L);
    }

    @Test
    void deleteTag() {
        TagBasicDto tagBasicDto = tagService.deleteTag(3L);
        assertThat(tagBasicDto.getTagNo()).isEqualTo(3L);
    }
}
