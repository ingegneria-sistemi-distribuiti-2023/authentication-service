package com.isd.authentication.config;

import com.isd.authentication.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthFilter;
	// AuthenticationProvider is an interface that provides an authenticate() method
	// to authenticate an instance of Authentication
	// its bean is implemented in ApplicationConfig
	private final AuthenticationProvider authenticationProvider;

	/*
	 * SecurityFilterChain is used by Spring Security to configure the http security
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf() // TODO: cercare (CROSS-SITE REQUEST FORGERY)
				.disable() // disable the CSRF (Cross-Site Request Forgery) protection

				.authorizeHttpRequests()
				.requestMatchers("/auth/jwt/**")
				.permitAll() // everyone can call the API on subpath /auth/jwt/**
				.anyRequest() // every other request must be authenticated
				.authenticated()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
				.and()
				.authenticationProvider(authenticationProvider)
				// the jwtAuthFilter will be called before the UsernamePasswordAuthenticationFilter
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
