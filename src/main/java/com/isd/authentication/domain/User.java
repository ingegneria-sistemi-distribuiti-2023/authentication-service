package com.isd.authentication.domain;

import com.isd.authentication.commons.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {

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

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    // implemented also for spring userdetails support
    private Role role;

    // implemented for UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // every user has only one role
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    // implemented for UserDetails
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // implemented for UserDetails
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // implemented for UserDetails
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // implemented for UserDetails
    @Override
    public boolean isEnabled() {
        return getEnabled();
    }
}
