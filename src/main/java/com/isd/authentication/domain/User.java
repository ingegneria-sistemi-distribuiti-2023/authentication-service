package com.isd.authentication.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45)
    private String username;

    @Column(length = 45)
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    /**
     * Mandatory to handle tests
     * */
    @Override
    public boolean equals(Object obj) {
        boolean same = false;

        if (obj != null && obj instanceof User){
            same = this.id == ((User) obj).getId() && this.username == ((User) obj).getUsername() && this.password == ((User) obj).getPassword() && this.enabled == ((User) obj).getEnabled();
        }

        return same;
    }
}
