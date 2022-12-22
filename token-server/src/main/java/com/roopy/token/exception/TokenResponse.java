package com.roopy.token.exception;

public class TokenResponse {

    String message;

    public TokenResponse(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}