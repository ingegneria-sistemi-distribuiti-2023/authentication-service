package com.isd.authentication.repository;

import com.isd.authentication.domain.Balance;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BalanceRepository extends JpaRepository<Balance, Integer> {

    Balance findByUserId(Integer id);

    void deleteByUserId(Integer userId);
}
