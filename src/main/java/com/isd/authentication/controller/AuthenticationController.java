package com.isd.authentication.controller;

import com.isd.authentication.auth.TokenProvider;
import com.isd.authentication.domain.User;
import com.isd.authentication.dto.LoginRequest;
import com.isd.authentication.dto.TokenResponse;
import com.isd.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    UserRepository ur;

    @PostMapping(value = "/authenticate")
    public @ResponseBody TokenResponse authenticate(@RequestBody LoginRequest request) throws Exception {
        // Validate the request
        TokenResponse toRes = new TokenResponse();

        try {

            if (!authenticateUser(request.getUserId(), request.getPassword())){
                throw new Exception("User not found");
            }

            String jwt = createJWT(request.getUserId());

            toRes.setMessage(jwt);

        } catch (Error e){
            new Exception(e.getMessage());
        }

        return toRes;
    }

    private boolean isRequestValid(String token) {
        // validate the request here
        return tokenProvider.isTokenValid(token);
    }

    private boolean authenticateUser(Integer userId, String password) {
        // authenticate the user, for example by checking against a database

        User currentUser = ur.findByIdAndPassword(userId, password);

        if (currentUser == null){
            return false;
        }

        return true;
    }

    private String createJWT(Integer id ) { //, String issuer, String subject, long ttlMillis) {
        // create JWT token
        return tokenProvider.generateJwt(id);
    }
}