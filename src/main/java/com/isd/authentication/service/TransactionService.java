package com.isd.authentication.service;

import com.isd.authentication.commons.TransactionStatus;
import com.isd.authentication.commons.TransactionType;
import com.isd.authentication.domain.Balance;
import com.isd.authentication.domain.Transaction;
import com.isd.authentication.domain.User;
import com.isd.authentication.dto.TransactionDTO;
import com.isd.authentication.dto.TransactionRequestDTO;
import com.isd.authentication.dto.TransactionResponseDTO;
import com.isd.authentication.repository.BalanceRepository;
import com.isd.authentication.repository.TransactionRepository;
import com.isd.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class TransactionService {

    @Autowired
    BalanceRepository br;

    @Autowired
    TransactionRepository tr;

    @Autowired
    UserRepository ur;

    Transaction transaction;

    Balance balance;

    private void processTransaction(TransactionRequestDTO request, TransactionType type) throws Exception {
        if (request.getAmount() <= 0 || request.getCircuit() == null || request.getCircuit() == null) {
            throw new Exception("Something wrong on request");
        }
        User user = ur.findOneById(request.getUserId());
        if (user == null || user.getEnabled() == false) {
            throw new Exception("User not found or not enabled");
        }

        balance = br.findByUserId(user.getId());
        if (balance == null) {
            throw new Exception("Balance not found");
        }

        transaction = new Transaction();
        transaction.setCategory(type);
        transaction.setUserId(user.getId());
        transaction.setCircuit(request.getCircuit());
        transaction.setAmount(request.getAmount());
        transaction.setStatus(TransactionStatus.OPEN);
        transaction.setDate(new Date());
        tr.save(transaction);
    }

    public TransactionResponseDTO deposit(TransactionRequestDTO request)  throws Exception{
        TransactionResponseDTO toRet = new TransactionResponseDTO();
        processTransaction(request,TransactionType.DEPOSIT);
        Float currentBalanceCashable = balance.getCashable();
        Float updatedBalanceCashable = currentBalanceCashable + request.getAmount();
        balance.setCashable(updatedBalanceCashable);
        br.save(balance);

        transaction.setStatus(TransactionStatus.CLOSED);
        toRet.setStatus(transaction.getStatus());
        toRet.setMessage("Success");
        toRet.setTime(transaction.getDate());

        return toRet;
    }

    public TransactionResponseDTO withdraw(TransactionRequestDTO request) throws Exception {
        TransactionResponseDTO toRet = new TransactionResponseDTO();
        processTransaction(request,TransactionType.WITHDRAW);
        if(request.getAmount() > balance.getCashable()) {
            throw new Exception("Insufficient funds on balance");
        }
        Float currentBalanceCashable = balance.getCashable();
        Float updatedBalanceCashable = currentBalanceCashable - request.getAmount();
        balance.setCashable(updatedBalanceCashable);
        br.save(balance);

        transaction.setStatus(TransactionStatus.CLOSED);
        toRet.setStatus(transaction.getStatus());
        toRet.setMessage("Success");
        toRet.setTime(transaction.getDate());

        return toRet;
    }


}
