package com.isd.authentication.mapper;

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

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class is used to make conversion and manage the User entity between entity
 * and dto ( has the role of assembler ? )
 *
 * **/
// TODO: Da rinominare in converter
@Service
@Transactional
public class UserMapperService {

    @Autowired
    private UserRepository ur;

    @Autowired
    private BalanceRepository br;

    @Autowired
    private TransactionRepository tr;

    @Autowired
    TransactionConverter trmps;

    public List<UserBalanceDTO> getAll() {
        return ur.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserBalanceDTO convertToDTO(User user){
        UserBalanceDTO dto = new UserBalanceDTO();

        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEnabled(user.getEnabled());

        Balance currB = br.findByUserId(user.getId());

        // ho evitato il throw dell'errore qui per evitare di gestire su getAll e fare ritornare un oggetto totalmente null
        if (currB != null){
            dto.setCashableAmount(currB.getCashable());
            dto.setBonusAmount(currB.getBonus());
        }

        return dto;
    }

    public UserBalanceDTO createUser(UserCreateDTO current) throws Exception{

        if (ur.findByUsername(current.getUsername()) != null ) {
            throw new Exception("Username already used");
        }

        User newuser = new User();
        newuser.setUsername(current.getUsername());
        newuser.setPassword(current.getPassword());
        newuser.setEnabled(true);
        ur.save(newuser);

        Balance balance = new Balance();
        balance.setUserId(newuser.getId());
        balance.setBonus(0.0f);
        balance.setCashable(0.0f);
        br.save(balance);

        UserBalanceDTO toRet = new UserBalanceDTO(newuser.getId(), newuser.getUsername(), balance.getCashable(), balance.getBonus(), newuser.getEnabled());

        return toRet;
    }

    public UserBalanceDTO deleteUser(Integer id) throws Exception {
        User current = ur.findOneById(id);

        if (current == null){
            throw new Exception("User not found");
        }

        current.setEnabled(false);
        ur.save(current);

        // added only for test if works elimination on db
        UserBalanceDTO toRet = this.convertToDTO(current);

//        tr.deleteAllByUserId(current.getId());
//        br.deleteByUserId(current.getId());
//        ur.delete(current);

        return toRet;

    }

    public UserBalanceTransDTO findUserById(Integer id) throws Exception {
        User usr = ur.findOneById(id);

        if (usr == null){
            throw new Exception("User not found");
        }

        UserBalanceTransDTO toRet = new UserBalanceTransDTO();

        toRet.setUserId(usr.getId());
        toRet.setUsername(usr.getUsername());
        toRet.setEnabled(usr.getEnabled());

        Balance bal = br.findByUserId(toRet.getUserId());

        toRet.setBonusAmount(bal.getBonus());
        toRet.setCashableAmount(bal.getCashable());
        List<Transaction> allTrs = tr.findAllByUserId(id);

        List<TransactionDTO> allTrsDto = new LinkedList<>();

        for (Transaction t: allTrs){
            allTrsDto.add(trmps.convertToDTO(t));
        }

        toRet.setTransactions(allTrsDto);

        return toRet;
    }

    public TransactionResponseDTO recharge(TransactionDTO request) throws Exception {
        TransactionResponseDTO response = new TransactionResponseDTO();

        User usr = ur.findOneById(request.getUserId());

        if (usr == null){
            throw new Exception("User not found");
        }

        Transaction newtr = new Transaction();

        // TODO: add timer to simulate process
        newtr.setUserId(usr.getId());
        newtr.setDate(new Date());
        newtr.setCircuit(request.getCircuit());
        newtr.setAmount(request.getAmount());

        // TODO: add logic to handle status
        newtr.setStatus(TransactionStatus.CLOSED);
        tr.save(newtr);

        Balance currBal = br.findByUserId(usr.getId());

        Float totCash = currBal.getCashable() + newtr.getAmount();

        currBal.setCashable(totCash);
        br.save(currBal);

        response.setMessage("Successful increase");
        response.setTime(new Date());
        response.setStatus(newtr.getStatus());

        return response;
    }

}
