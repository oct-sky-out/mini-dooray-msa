package com.nhnacademy.minidoorayprojectmanagementapi.exceptions;

public class DoseNotEqualProjectNo extends RuntimeException {
    public DoseNotEqualProjectNo() {
        super("같은 프로젝트의 업무가 아닙니다.");
    }
}
