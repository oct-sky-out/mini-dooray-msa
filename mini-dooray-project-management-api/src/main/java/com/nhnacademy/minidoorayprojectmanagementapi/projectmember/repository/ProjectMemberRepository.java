package com.nhnacademy.minidoorayprojectmanagementapi.projectmember.repository;

import com.nhnacademy.minidoorayprojectmanagementapi.projectmember.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
}
