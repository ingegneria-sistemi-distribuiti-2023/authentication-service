package com.isd.authentication.dto;

/**
 * DTO utilizzato per creare un utente
 */
public class UserCreateDTO {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
