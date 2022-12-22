package com.roopy.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private ExceptionEnum error;

    public AppException(ExceptionEnum e) {
        super(e.getMessage());
        this.error = e;
    }
}
