package com.isd.authentication.service;

import com.isd.authentication.commons.Role;
import com.isd.authentication.commons.TransactionStatus;
import com.isd.authentication.converter.TransactionConverter;
import com.isd.authentication.domain.Balance;
import com.isd.authentication.domain.Transaction;
import com.isd.authentication.domain.User;
import com.isd.authentication.dto.*;
import com.isd.authentication.repository.BalanceRepository;
import com.isd.authentication.repository.TransactionRepository;
import com.isd.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository ur;

    @Autowired
    private BalanceRepository br;

    @Autowired
    private TransactionRepository tr;

    @Autowired
    TransactionConverter trmps;

    private UserBalanceDTO mapToUserBalanceDTO(User user) {
        UserBalanceDTO dto = new UserBalanceDTO();
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEnabled(user.getEnabled());

        Balance currB = br.findByUserId(user.getId());
        dto.setCashableAmount(currB.getCashable());
        dto.setBonusAmount(currB.getBonus());
        return dto;
    }

    public List<UserBalanceDTO> getAll() throws Exception{
        List<User> allUsers = ur.findAll();
        return allUsers.stream()
                .map(this::mapToUserBalanceDTO)
                .collect(Collectors.toList());
    }

    public User createUserEntity(UserRegistrationDTO current) throws Exception{

        if (ur.findByUsername(current.getUsername()) == null ) {
            throw new Exception("Username already used");
        }

        User newuser = new User();
        newuser.setUsername(current.getUsername());
        newuser.setPassword(current.getPassword());
        newuser.setEnabled(true);
        newuser.setRole(Role.USER);
        User saved = ur.save(newuser);

        Balance balance = new Balance();
        balance.setUserId(newuser.getId());
        balance.setBonus(0.0f);
        balance.setCashable(0.0f);
        br.save(balance);

        return saved;
    }

    public UserBalanceDTO disableOrEnable(Integer id, Boolean toSet) throws Exception {
        User current = ur.findById(id).orElseThrow(() -> new Exception("User not found"));
        current.setEnabled(toSet);
        ur.save(current);
        return mapToUserBalanceDTO(current);
    }

    public UserBalanceTransDTO findUserById(Integer id) throws Exception {
        User usr = ur.findById(id).orElseThrow(() -> new Exception("User not found"));
        UserBalanceTransDTO toRet = new UserBalanceTransDTO();
        toRet.setUserId(usr.getId());
        toRet.setUsername(usr.getUsername());
        toRet.setEnabled(usr.getEnabled());

        Balance bal = br.findByUserId(toRet.getUserId());
        toRet.setBonusAmount(bal.getBonus());
        toRet.setCashableAmount(bal.getCashable());
        List<Transaction> allTrs = tr.findAllByUserId(id);
        List<TransactionDTO> allTrsDto = allTrs.stream().map(trmps::convertToDTO).collect(Collectors.toList());
        toRet.setTransactions(allTrsDto);

        return toRet;
    }

}
