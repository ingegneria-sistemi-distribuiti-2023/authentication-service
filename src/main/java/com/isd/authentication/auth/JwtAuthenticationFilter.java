package com.isd.authentication.auth;

import com.isd.authentication.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor // this annotation create a constructor with all the final fields
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
		/*
		* UserDetailsService is a spring security interface that allows to get the user
		* details given the username
*/	
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain // used to call successive filters
	) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization"); // extract the token from the header
		final String jwt;
		final String userName;
		// if the token is not present or it doesn't start with Bearer
		if (authHeader == null || !authHeader.startsWith("Bearer ")) { 
			// nothing will be executed by this filter, the request will be passed to the next filter
				filterChain.doFilter(request, response);
			return;
		}
		// extract from the header the jwt token, removing the "Bearer " part
		jwt = authHeader.substring(7); 
		
		// extract the username from the token
		userName = jwtService.extractUsername(jwt);
		// if the username is present and the user is not authenticated,
		// the securityContextHolder is checked too in order to make sure that the user is not already authenticated
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			if (jwtService.isTokenValid(jwt, userDetails)) {
				// if the token is valid a new UsernamePasswordAuthenticationToken is created, necessary to update the security context
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails,
						null,
						userDetails.getAuthorities()
				);
				// the user details are added to the token
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// the token is set as the authentication for the current user
				// this will update the security context, so that the user is now authenticated
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		// the next filter in the chain will be called
		filterChain.doFilter(request, response);
	}
}
