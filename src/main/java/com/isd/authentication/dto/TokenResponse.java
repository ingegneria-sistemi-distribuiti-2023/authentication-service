package com.isd.authentication.dto;

public class TokenResponse {

    private String message;

    public TokenResponse() {
    }

    public TokenResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
