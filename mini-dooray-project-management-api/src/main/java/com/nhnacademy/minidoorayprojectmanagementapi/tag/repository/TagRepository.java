package com.nhnacademy.minidoorayprojectmanagementapi.tag.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>, TagRepositoryCustom {
}
