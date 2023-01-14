package com.isd.authentication.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class TokenProvider {
    private final long VALIDITY_MS = 36000000; // 10h
    private final String secretKey = "my-secret-to-handle-all-request-protected";
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes());

    public String generateJwt(Integer userId) {

        Claims claims = Jwts.claims().setSubject(userId.toString());
        // TODO:
        claims.put("roles", "admin");
        Date now = new Date();
        Date validity = new Date(now.getTime() + VALIDITY_MS);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            return false;
        }
    }
}
