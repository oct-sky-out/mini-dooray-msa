package com.nhnacademy.minidoorayprojectmanagementapi.exceptions;

public class IncludedDifferentTaskNoException extends RuntimeException {
    public IncludedDifferentTaskNoException() {
        super("다른 작업 번호를 포함하고있습니다.");
    }
}
