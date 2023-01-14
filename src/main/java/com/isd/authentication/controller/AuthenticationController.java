package com.isd.authentication.controller;

import com.isd.authentication.auth.AuthenticationResponse;
import com.isd.authentication.auth.AuthenticationService;
import com.isd.authentication.dto.LoginRequest;
import com.isd.authentication.dto.UserRegistrationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/jwt/")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register (
      @RequestBody UserRegistrationDTO request
  ) throws Exception {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody LoginRequest request
  ) throws Exception{
    return ResponseEntity.ok(service.authenticate(request));
  }


}
