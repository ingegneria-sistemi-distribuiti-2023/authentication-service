package com.isd.authentication.repository;

import com.isd.authentication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();
    User findOneById(Integer id);
    Optional<User> findByUsername(String username);
    User findOneByUsername(String username);
    User findOneByUsernameAndPassword(String username, String password );
    User findByIdAndPassword(Integer id, String password);
    User save(User u);
    void delete(User u);
    boolean existsById(Integer id);

}
