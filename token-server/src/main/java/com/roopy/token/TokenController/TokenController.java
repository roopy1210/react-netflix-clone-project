package com.roopy.token.TokenController;

import com.roopy.token.crypto.AES256Cipher;
import com.roopy.token.security.jwt.payload.request.SignInRequest;
import com.roopy.token.security.jwt.payload.response.TokenResponse;
import com.roopy.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 토큰 관리를 위한 클래스
 */
@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    /**
     * 토큰 발급
     *
     * @param request 토큰 발급을 위한 ID, 비밀번호
     * @return ResponseEntity<Map<String,Object>> AccessToken, RefreshToken
     */
    @PostMapping("/token")
    public ResponseEntity<Map<String,Object>> createAccessTokenAndRefreshToken(@Valid @RequestBody SignInRequest request) throws BadCredentialsException {
        TokenResponse tokenResponse = tokenService.generateAccessTokenAndRefreshToken(request);

        Map<String,Object> retObj = new HashMap<>();
        retObj.put("accessToken", tokenResponse.getAccessToken());
        retObj.put("refreshToken", tokenResponse.getRefreshToken());
        retObj.put("expireDate", tokenResponse.getExpireDate());

        return new ResponseEntity<>(retObj, HttpStatus.OK);
    }

    /**
     * AccessToken 재발급
     *
     * @param refreshToken Base64로 암호화된 값이므로 서비스 호출전 Decoding 처리 해야 함
     * @return ResponseEntity<String> 재발급된 accessToken
     */
    @PostMapping("/reissue/accessToken")
    public ResponseEntity<String> reIssueAccessToken(@RequestBody String refreshToken) throws Exception {
        String accessToken = tokenService.generateAccessTokenWithRefreshToken(AES256Cipher.decrypt(refreshToken));

        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }
}
