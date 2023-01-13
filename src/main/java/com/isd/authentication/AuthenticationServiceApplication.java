package com.isd.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class AuthenticationServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }

}
