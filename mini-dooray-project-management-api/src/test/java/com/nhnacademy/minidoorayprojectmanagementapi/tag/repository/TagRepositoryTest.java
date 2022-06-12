package com.nhnacademy.minidoorayprojectmanagementapi.tag.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.project.entity.Project;
import com.nhnacademy.minidoorayprojectmanagementapi.project.repository.ProjectRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.tag.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TagRepositoryTest {
    @Autowired
    TagRepository tagRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void saveTagTest() {
        Project project = projectRepository.findById(1000L).get();
        Tag tag = Tag.builder()
            .project(project)
            .name("DO Task1")
            .build();
        Tag savedTag = tagRepository.saveAndFlush(tag);

        assertThat(savedTag.getProject().getProjectNo()).isEqualTo(project.getProjectNo());
        assertThat(savedTag.getName()).isEqualTo(tag.getName());
    }

    @Test
    void tagModifyTest() {
        Long result = tagRepository.modifyTag(1000L, 3L, "modify!!");
        assertThat(result).isEqualTo(1L);
    }
}
