package com.isd.authentication.controller;

import com.isd.authentication.auth.TokenProvider;
import com.isd.authentication.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Validate the login request using the database or any other method
        Integer userId = loginRequest.getUserId();
        String accessToken = tokenProvider.generateToken(userId);
        return ResponseEntity.ok(accessToken);
    }
}