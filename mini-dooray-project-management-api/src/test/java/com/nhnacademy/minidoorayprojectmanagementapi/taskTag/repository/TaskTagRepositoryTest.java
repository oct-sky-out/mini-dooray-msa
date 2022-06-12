package com.nhnacademy.minidoorayprojectmanagementapi.taskTag.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayprojectmanagementapi.tag.entity.Tag;
import com.nhnacademy.minidoorayprojectmanagementapi.tag.repository.TagRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.task.entity.Task;
import com.nhnacademy.minidoorayprojectmanagementapi.task.repository.TaskRepository;
import com.nhnacademy.minidoorayprojectmanagementapi.taskTag.entity.TaskTag;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskTagRepositoryTest {
    @Autowired
    TaskTagRepository taskTagRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TagRepository tagRepository;

    @Test
    void addTaskTegTest() {
        Task task = taskRepository.findById(8L).get();
        Tag tag1 = tagRepository.findById(3L).get();
        Tag tag2 = tagRepository.findById(26L).get();

        List<TaskTag> taskTags = new ArrayList<>();

        TaskTag.Pk pk1 = new TaskTag.Pk(task.getTaskNo(), tag1.getTagNo());
        TaskTag.Pk pk2 = new TaskTag.Pk(task.getTaskNo(), tag2.getTagNo());

        taskTags.add(TaskTag.builder()
            .pk(pk1)
            .task(task)
            .tag(tag1)
            .build());
        taskTags.add(TaskTag.builder()
            .pk(pk2)
            .task(task)
            .tag(tag2)
            .build());

        List<TaskTag> savedTaskTags = taskTagRepository.saveAllAndFlush(taskTags);
        assertThat(savedTaskTags.get(0)).isEqualTo(taskTags.get(0));
        assertThat(savedTaskTags.get(1)).isEqualTo(taskTags.get(1));
    }

    @Test
    void removeTaskTegTest() {
        List<TaskTag.Pk> taskTagPks = new ArrayList<>();

        TaskTag.Pk pk1 = new TaskTag.Pk(8L, 3L);
        TaskTag.Pk pk2 = new TaskTag.Pk(8L, 26L);
        taskTagPks.add(pk1);
        taskTagPks.add(pk2);

        int dropCount = taskTagRepository.dropTaskTag(taskTagPks);
        assertThat(dropCount).isEqualTo(2);
    }
}
