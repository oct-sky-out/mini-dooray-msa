package com.nhnacademy.minidoorayprojectmanagementapi.exceptions;

public class ProjectMemberNotFoundException extends RuntimeException {
    public ProjectMemberNotFoundException() {
        super("프로젝트 멤버를 찾을 수 없습니다.");
    }
}
