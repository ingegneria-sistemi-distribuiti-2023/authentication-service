package com.isd.authentication.repository;

import com.isd.authentication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findOneById(Integer id);
    User findByUsername(String username);
    User findByIdAndPassword(Integer id, String password);
    User save(User u);
    void delete(User u);
    boolean existsById(Integer id);

}
