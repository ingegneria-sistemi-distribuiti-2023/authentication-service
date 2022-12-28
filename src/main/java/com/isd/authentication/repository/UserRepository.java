package com.isd.authentication.repository;

import com.isd.authentication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
    User save(User u);
    void delete(User u);
}
