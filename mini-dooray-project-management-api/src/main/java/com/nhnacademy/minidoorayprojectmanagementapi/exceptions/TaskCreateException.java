package com.nhnacademy.minidoorayprojectmanagementapi.exceptions;

public class TaskCreateException extends RuntimeException {
    public TaskCreateException() {
        super("업무를 생성에 실패했습니다.");
    }
}
