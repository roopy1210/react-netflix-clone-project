package com.roopy.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {
    // 시스템 에러
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E002"),

    // Account 관련 에러
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "A001", "사용자 정보가 존재 하지 않습니다."),
    USER_EXISTED_EXCEPTION(HttpStatus.CREATED, "A002", "이미 가입되어 있는 사용자 입니다.");

    private final HttpStatus status;
    private final String code;
    private String message;


    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
