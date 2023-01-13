package com.isd.authentication.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIController {
    @GetMapping("/protected")
    public ResponseEntity<String> protectedAPI(@RequestHeader("Authorization") String authorizationHeader, HttpSession session) {
        String accessToken = authorizationHeader.substring("Bearer ".length());
        String sessionToken = (String) session.getAttribute("access_token");
        if (accessToken.equals(sessionToken)) {
            return ResponseEntity.ok("Protected API");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}