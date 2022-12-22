package com.roopy.token.service;

import com.roopy.token.security.jwt.payload.request.SignInRequest;
import com.roopy.token.security.jwt.payload.response.TokenResponse;
import org.springframework.security.authentication.BadCredentialsException;

/**
 * 토큰 관리 및 사용자 인증 관리를 위한 인터페이스
 */
public interface TokenService {


    /**
     * 토큰발행
     *
     * @param signInRequest - UsernamePasswordAuthenticationToken 객체 생성을 위한 파라미터 정보를 담고 있는 클래스
     * 파라미터정보: username,password
     * @return TokenDto - accessToken, refreshToken 정보를 담고 있는 객체
     */
    TokenResponse generateAccessTokenAndRefreshToken(SignInRequest signInRequest) throws BadCredentialsException;

    /**
     * AccessToken 재발행
     * <p>
     * accessToken 이 만료되고 refreshToken 이 유효한 경우
     * </p>
     *
     * @param refreshToken - 토큰 값은 Apache Common Base64 Decoding 처리 되어야한다.
     * @return accessToken
     */
    String generateAccessTokenWithRefreshToken(String refreshToken) throws Exception;

    /**
     * RefreshToken 재발행
     * <p>
     * accessToken 은 유효하고 refreshToken 이 만료된 경우
     * </p>
     *
     * @param accessToken 현재 유효한 accessToken
     * @return refreshToken
     */
    String generateRefreshTokenWithAccessToken(String accessToken) throws Exception;

    /**
     * AccessToken 유효성 체크
     *
     * @param accessToken the accessToken
     * @return true or false
     */
    boolean validateAccessToken(String accessToken);

    /**
     * RefreshToken 유효성 체크
     *
     * @param refreshToken - 토큰 값은 Apache Common Base64 Decoding 처리 되어야한다.
     * @return set true of false
     */
    boolean validateRefreshToken(String refreshToken);

    /**
     * 토큰 만료시 h2db, Redis에 저장된 토큰 정보 삭제 처리
     *
     * @param username 로그인 사용자
     */
    void deleteTokenFromUsername(String username);

}
