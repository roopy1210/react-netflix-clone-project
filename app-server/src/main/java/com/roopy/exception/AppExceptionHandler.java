package com.roopy.exception;

import com.roopy.dto.AppExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler({AppException.class})
    public ResponseEntity<AppExceptionDTO> exceptionHanlder(HttpServletRequest request, final AppException e) {
        return ResponseEntity
                .status(e.getError().getStatus())
                .body(AppExceptionDTO.builder()
                        .errorCode(e.getError().getCode())
                        .errorMessage("시스템 오류가 발생 하였습니다.")
                        .devErrorMessage(e.getError().getMessage())
                        .build());
    }

    @ExceptionHandler({ConnectException.class})
    public ResponseEntity<AppExceptionDTO> exceptionHanlder(HttpServletRequest request, final ConnectException e) {
        return ResponseEntity
                .status(ExceptionEnum.RUNTIME_EXCEPTION.getStatus())
                .body(AppExceptionDTO.builder()
                        .errorCode(ExceptionEnum.RUNTIME_EXCEPTION.getCode())
                        .errorMessage("서버 접속이 원활 하지 않습니다.")
                        .devErrorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler({HttpClientErrorException.Unauthorized.class})
    public ResponseEntity<AppExceptionDTO> exceptionHanlder(HttpServletRequest request, final HttpClientErrorException.Unauthorized e) {
        return ResponseEntity
                .status(ExceptionEnum.UNAUTHORIZED_EXCEPTION.getStatus())
                .body(AppExceptionDTO.builder()
                        .errorCode(ExceptionEnum.UNAUTHORIZED_EXCEPTION.getCode())
                        .errorMessage(ExceptionEnum.UNAUTHORIZED_EXCEPTION.getMessage())
                        .devErrorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler({TokenException.class})
    public ResponseEntity<AppExceptionDTO> exceptionHanlder(HttpServletRequest request, final TokenException e) {
        return ResponseEntity
                .status(ExceptionEnum.UNAUTHORIZED_EXCEPTION.getStatus())
                .body(AppExceptionDTO.builder()
                        .errorCode(ExceptionEnum.UNAUTHORIZED_EXCEPTION.getCode())
                        .errorMessage(ExceptionEnum.UNAUTHORIZED_EXCEPTION.getMessage())
                        .devErrorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<AppExceptionDTO> exceptionHanlder(HttpServletRequest request, final RuntimeException e) {
        return ResponseEntity
                .status(ExceptionEnum.USER_EXISTED_EXCEPTION.getStatus())
                .body(AppExceptionDTO.builder()
                        .errorCode(ExceptionEnum.USER_EXISTED_EXCEPTION.getCode())
                        .errorMessage(ExceptionEnum.USER_EXISTED_EXCEPTION.getMessage())
                        .devErrorMessage(e.getMessage())
                        .build());
    }
}
