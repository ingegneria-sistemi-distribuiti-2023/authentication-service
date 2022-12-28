package com.isd.authentication.mapper;

import com.isd.authentication.domain.Balance;
import com.isd.authentication.domain.User;
import com.isd.authentication.dto.UserBalanceTransactionDTO;
import com.isd.authentication.dto.UserCreateDTO;
import com.isd.authentication.repository.BalanceRepository;
import com.isd.authentication.repository.TransactionRepository;
import com.isd.authentication.repository.UserRepository;
import com.isd.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * This class is used to make conversion and manage the User entity between entity
 * and dto ( has the role of assembler ? )
 *
 * **/
@Service
@Transactional
public class UserMapperService {

    @Autowired
    private UserRepository ur;

    @Autowired
    private BalanceRepository br;

    @Autowired
    private TransactionRepository tr;

    public List<UserBalanceTransactionDTO> getAll() {
        return ur.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserBalanceTransactionDTO convertToDTO(User user){
        UserBalanceTransactionDTO dto = new UserBalanceTransactionDTO();

        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());

        Balance currB = br.findByUserId(user.getId());

        // ho evitato il throw dell'errore qui per evitare di gestire su getAll e fare ritornare un oggetto totalmente null
        if (currB != null){
            dto.setCashableAmount(currB.getCashable());
            dto.setBonusAmount(currB.getBonus());
        }

        return dto;
    }

    public UserBalanceTransactionDTO createUser(UserCreateDTO current) throws Exception{

        if (ur.findByUsername(current.getUsername()) != null ) {
            throw new Exception("Username already used");
        }

        User newuser = new User();
        newuser.setUsername(current.getUsername());
        newuser.setPassword(current.getPassword());
        ur.save(newuser);

        Balance balance = new Balance();
        balance.setUserId(newuser.getId());
        balance.setBonus(0);
        balance.setCashable(0);
        br.save(balance);

        UserBalanceTransactionDTO toRet = new UserBalanceTransactionDTO(newuser.getId(), newuser.getUsername(), balance.getCashable(), balance.getBonus());

        return toRet;
    }

    public UserBalanceTransactionDTO deleteUser(String username) throws Exception {

        User current = ur.findByUsername(username);

        if (current == null){
            throw new Exception("User not found");
        }

        UserBalanceTransactionDTO toRet = this.convertToDTO(current);

        br.deleteByUserId(current.getId());
        ur.delete(current);

        return toRet;

    }

}
