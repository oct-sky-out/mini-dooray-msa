package com.nhnacademy.minidooraygateway.exceptions;

public class HasNotUserEmailException extends RuntimeException {
    public HasNotUserEmailException() {
        super("이메일이 존재하지않습니다.");
    }
}
