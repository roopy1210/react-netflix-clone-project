package com.roopy.exception;

import lombok.Getter;
import org.apache.tomcat.websocket.AuthenticationException;

@Getter
public class TokenException extends AuthenticationException {
    public TokenException(String errorMessage) {
        super(errorMessage);
    }
}
