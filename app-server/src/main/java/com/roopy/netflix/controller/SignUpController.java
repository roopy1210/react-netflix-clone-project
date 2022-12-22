package com.roopy.netflix.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roopy.dto.UserDTO;
import com.roopy.netflix.controller.SignInController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
public class SignUpController {

    private static final Logger logger = LoggerFactory.getLogger(SignInController.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 로그인
     *
     * <p>
     *     1.account-server 사용자 등록
     * </p>
     *
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param userDTO set username and password
     * @return TokenResponse accessToken
     * @throws Exception exception
     */
    @PostMapping("/signup")
    public ResponseEntity<ResponseEntity> signup(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , @Valid @RequestBody UserDTO userDTO) throws Exception {

        // 사용자등록
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();

        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(userDTO), headers);
        ResponseEntity<HashMap> userEntity = restTemplate.postForEntity("http://localhost:10000/users", request, HashMap.class);

        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }
    
}
