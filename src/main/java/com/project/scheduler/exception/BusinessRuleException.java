package com.project.scheduler.exception;

import org.springframework.http.HttpStatus;

public class BusinessRuleException extends RuntimeException {
    private final HttpStatus status;

    public BusinessRuleException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
