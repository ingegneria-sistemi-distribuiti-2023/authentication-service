package com.isd.authentication.auth;

import org.apache.el.parser.Token;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider jwtTokenProvider;

    public SecurityConfig(TokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilter(new JwtAuthorizationFilter())
//                .addFilter(new JwtAuthorizationFilter())
//                .authorizeRequests()
//                .antMatchers("/api/admin/**").hasRole("ADMIN")
//                .antMatchers("/api/**").authenticated()
//                .anyRequest().permitAll()
//                .and()
//                .csrf().disable();
    }
}