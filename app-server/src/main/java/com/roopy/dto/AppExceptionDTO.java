package com.roopy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class AppExceptionDTO {
    private String errorCode;
    private String errorMessage;
    private String devErrorMessage;

    @Builder
    public AppExceptionDTO(HttpStatus status, String errorCode, String errorMessage, String devErrorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.devErrorMessage = devErrorMessage;
    }
}
