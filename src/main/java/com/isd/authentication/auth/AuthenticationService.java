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
  private final UserRepository repository;
  private final UserService us;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(UserRegistrationDTO request) throws Exception{
    request.setPassword(passwordEncoder.encode(request.getPassword()));
    User user = us.createUserEntity(request);

    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(LoginRequest request) {

    var user = repository.findByUsername(request.getUsername())
            .orElseThrow();

//    List<GrantedAuthority> roles = (List<GrantedAuthority>) user.getAuthorities();

    Authentication auth = new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
//            roles
    );

    authenticationManager.authenticate(auth);

    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
  }
}
