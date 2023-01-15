package com.isd.authentication.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

	// this secret key is used to sign the token and to verify it
	private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

	public String extractUsername(String token) {
		// a Claim is an information contained in the token, in this case it is the username 
		return extractClaim(token, Claims::getSubject);
	}

	/*
	 * 1. token: jwt token
	 * 2. claimsResolver: a function that given an object and a claims type return
	 * an object of type T
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

		/*
		 * extraClaims: additional claims that you want to insert in the token
		 * userDetails: userdetails that you want to insert in the token, of spring security, contains username and password
		 */
	public String generateToken(
			Map<String, Object> extraClaims,
			UserDetails userDetails 
	) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				// insert the username as claim
				.setSubject(userDetails.getUsername()) 
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // 24 minutes
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact(); // create the token
	}

	/*
	 * 1. token: jwt token
	 * 2. userDetails: userDetails that need to be verified
	 * check if the given username is the same as the one in the token and if the
	 * token is not expired
	 */
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey()) // used to decode the token
				.build()
				.parseClaimsJws(token)
				.getBody(); // this is the token body, it contains the claims
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
