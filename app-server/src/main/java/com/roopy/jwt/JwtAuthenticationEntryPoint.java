package com.roopy.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roopy.dto.AppExceptionDTO;
import com.roopy.exception.ExceptionEnum;
import com.roopy.exception.TokenException;
import net.bytebuddy.description.ByteCodeElement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 401 Unauthorized 에러 처리를 위한 클래스
 * - SecurityConfig 에 exceptionHandling 등록 필요
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException {

        final Map<String, Object> body = new HashMap<>();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 응답 객체 초기화
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("errorCode", ExceptionEnum.UNAUTHORIZED_EXCEPTION.getCode());
        body.put("errorMessage", "사용자 정보가 올바르지 않습니다.");
        body.put("devErrorMessage", e.getMessage());
        final ObjectMapper mapper = new ObjectMapper();
        // response 객체에 응답 객체를 넣어줌
        mapper.writeValue(response.getOutputStream(), body);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    }
}
