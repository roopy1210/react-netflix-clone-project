package com.roopy.netflix.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roopy.jwt.JwtUtil;
import com.roopy.jwt.payload.request.SignInRequest;
import com.roopy.jwt.payload.response.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class SignInController {

    private static final Logger logger = LoggerFactory.getLogger(SignInController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 로그인
     *
     * <p>
     *     1.인증서버에 AccessToken, RefreshToken 발급요청
     * </p>
     *
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param signInRequest set username and password
     * @return TokenResponse accessToken
     * @throws Exception exception
     */
    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> signin(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , @Valid @RequestBody SignInRequest signInRequest) throws Exception {

        // 토큰 발행 요청
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();

        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(signInRequest), headers);
        ResponseEntity<HashMap> tokenIssueResponseEntity = null;
        try {
            tokenIssueResponseEntity = restTemplate.postForEntity("http://localhost:10001/token", request, HashMap.class);
        } catch (Exception e) {
            throw new ConnectException("서버 접속이 원활하지 않습니다.");
        }

        // 토큰 발행 요청 결과
        String accessToken;
        long expirationTime;

        TokenResponse tokenResponse = null;

        if (tokenIssueResponseEntity.getStatusCodeValue() == HttpStatus.OK.value()) {
            accessToken = (String) tokenIssueResponseEntity.getBody().get("accessToken");
            expirationTime = Long.parseLong(String.valueOf(tokenIssueResponseEntity.getBody().get("expireDate")));

            Authentication authentication = jwtUtil.getAuthentication(accessToken);

            User user = (User) authentication.getPrincipal();
            List<String> roles = new ArrayList<>();
            for (GrantedAuthority item : user.getAuthorities()) {
                String authority = item.getAuthority();
                roles.add(authority);
            }

            tokenResponse = TokenResponse.builder()
                    .accessToken(accessToken)
                    .expireDate(expirationTime)
                    .roles(roles)
                    .build();
        }

        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }

}
