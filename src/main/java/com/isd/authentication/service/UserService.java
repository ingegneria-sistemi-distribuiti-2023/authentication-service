package com.isd.authentication.service;

import com.isd.authentication.domain.Balance;
import com.isd.authentication.domain.User;
import com.isd.authentication.repository.BalanceRepository;
import com.isd.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// FIXME: to remove
// FIXME: chiedi conferma, ma non sarà più necessario usare questa classe perché usi già UserMapperService

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BalanceRepository balanceRepository;

    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    public User create(User u){
        // TODO: verifica che sia not null eventualmente throw dell'errore !
        userRepository.save(u);

        Balance b = new Balance();
        b.setUserId(u.getId());
        b.setCashable(0);
        b.setBonus(0);

        return u;
    }
}
