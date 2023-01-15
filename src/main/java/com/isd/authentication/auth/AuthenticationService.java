package com.isd.authentication.auth;

import com.isd.authentication.domain.User;
import com.isd.authentication.dto.LoginRequest;
import com.isd.authentication.dto.UserRegistrationDTO;
import com.isd.authentication.repository.UserRepository;
import com.isd.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  // those final fields are injected by spring, they are beans that are already
  // defined in the application context

  // used in order to retrieve the user from the db
  private final UserRepository repository;
  private final UserService us;
  // used to encode the password
  private final PasswordEncoder passwordEncoder;
  // used to generate the token
  private final JwtService jwtService;
  // used to authenticate the request, it already has the authentication methods implemented by spring
  private final AuthenticationManager authenticationManager;


  public AuthenticationResponse register(UserRegistrationDTO request) throws Exception {
    request.setPassword(passwordEncoder.encode(request.getPassword()));
    User user = us.createUserEntity(request);

    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(LoginRequest request) {
    // TODO: move this after the authentication
    var user = repository.findByUsername(request.getUsername())
        .orElseThrow();

    Authentication auth = new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword()
    );
    // authenticationManager is a bean of spring that already has the authentication methods implemented
    authenticationManager.authenticate(auth);
    // if this code is being evaluated it means that the authentication was successful
    // it's now possible to generate the token
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }
}
