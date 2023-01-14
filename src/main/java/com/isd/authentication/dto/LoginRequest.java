package com.isd.authentication.dto;

public class LoginRequest {
    private Integer userId;
    private String password;

    public LoginRequest() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}