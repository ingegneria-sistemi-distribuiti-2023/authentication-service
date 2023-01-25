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
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth/jwt/")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService service;
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register (@RequestHeader("Secret-Key") String secret, @RequestBody UserRegistrationDTO request) throws Exception {
    return new ResponseEntity<>(service.register(request), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestHeader("Secret-Key") String secret, @RequestBody LoginRequest request) throws Exception {TimeUnit.SECONDS.sleep(1);
//    TimeUnit.SECONDS.sleep(10);
    return new ResponseEntity<>(service.authenticate(request), HttpStatus.OK);
  }

  @PostMapping("/validate")
  public ResponseEntity<Boolean> validate(@RequestHeader("Secret-Key") String secret, @RequestBody ValidationRequest request) throws Exception {
    return new ResponseEntity<>(service.validateToken(request.getUsername(), request.getJwtToken()), HttpStatus.OK);
  }
}
