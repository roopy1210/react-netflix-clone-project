package com.roopy.token.service.impl;

import com.roopy.token.component.TokenProvider;
import com.roopy.token.crypto.AES256Cipher;
import com.roopy.token.entity.RefreshToken;
import com.roopy.token.repository.RefreshTokenRepository;
import com.roopy.token.security.jwt.payload.request.SignInRequest;
import com.roopy.token.security.jwt.payload.response.TokenResponse;
import com.roopy.token.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public TokenResponse generateAccessTokenAndRefreshToken(SignInRequest signInRequest) throws BadCredentialsException {
        // Expire Date 설정
        // Adding 10 mins using Date constructor.
        Calendar date = Calendar.getInstance();
        long timeInSecs = date.getTimeInMillis();
        Date afterAddingParamMins = new Date(timeInSecs + (5 * 60 * 1000));

        // 1. accessToken 생성
        // CustomUserDetailService 꼭 구현 해야한다.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String accessToken = tokenProvider.createToken(authentication);
        logger.debug("AccessToken({}) 이 정상적으로 발급 되었습니다.", accessToken);

        // 2. refreshToken 생성
        RefreshToken refreshTokenRequest = RefreshToken.builder()
                .id(UUID.randomUUID().toString())
                .username(signInRequest.getUsername())
                .expirationDurationTime(afterAddingParamMins.getTime())
                .password(AES256Cipher.encrypt(signInRequest.getPassword()))
                .build();

        RefreshToken refreshTokenResponse = refreshTokenRepository.save(refreshTokenRequest);
        logger.debug("RefreshToken({}) 이 정상적으로 발급 되었습니다.", refreshTokenResponse.getId());

        // RefreshToken 재발급후 AccessToken 재발급시 Encoding 되지 않은 사용자의 비밀번호가 필요 한데
        // Encoding 전의 비밀번호는 Redis 서버에에 저장 하고 있는데 RefreshToken 만료시 쿠키 정보가 삭제
        // 되므로 사용자의 비밀번호를 알 수 없으므로 사용자 테이블에 RefreshToken 발급전 토큰 정보를 업데이트 처리한다.
        /*
        TODO::account-server 에 로직 옮기기
        Optional<User> user =  userRepository.findOneWithAuthoritiesByUsername(loginRequest.getUsername());
        user.get().setUid(AES256Cipher.encrypt(refreshTokenResponse.getId()));
        userRepository.save(user.get());
        */

        // 3. return 객체 생성
        TokenResponse tokenResponse = TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenResponse.getId())
                .expireDate(afterAddingParamMins.getTime())
                .authentication(authentication)
                .build();

        return tokenResponse;
    }

    @Override
    public String generateAccessTokenWithRefreshToken(String refreshToken) throws Exception {
        return null;
    }

    @Override
    public String generateRefreshTokenWithAccessToken(String accessToken) throws Exception {
        return null;
    }

    @Override
    public boolean validateAccessToken(String accessToken) {
        return false;
    }

    @Override
    public boolean validateRefreshToken(String refreshToken) {
        return false;
    }

    @Override
    public void deleteTokenFromUsername(String username) {

    }
}
