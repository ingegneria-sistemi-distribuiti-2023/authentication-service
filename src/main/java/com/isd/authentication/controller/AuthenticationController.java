package com.isd.authentication.controller;

import com.isd.authentication.auth.AuthenticationResponse;
import com.isd.authentication.auth.AuthenticationService;
import com.isd.authentication.dto.LoginRequest;
import com.isd.authentication.dto.UserRegistrationDTO;
import com.isd.authentication.dto.ValidationRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register (@RequestBody UserRegistrationDTO request) throws Exception {
    return new ResponseEntity<>(service.register(request), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody LoginRequest request) throws Exception {
    return new ResponseEntity<>(service.authenticate(request), HttpStatus.OK);
  }

  @PostMapping("/validate")
  public ResponseEntity<Boolean> validate(@RequestBody ValidationRequest request) throws Exception {
    return new ResponseEntity<>(service.validateToken(request.getUsername(), request.getJwtToken()), HttpStatus.OK);
  }
}
