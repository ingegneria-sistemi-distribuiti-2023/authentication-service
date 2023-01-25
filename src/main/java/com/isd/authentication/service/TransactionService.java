package com.isd.authentication.service;

import com.isd.authentication.commons.TransactionStatus;
import com.isd.authentication.commons.TransactionType;
import com.isd.authentication.commons.error.CustomHttpResponse;
import com.isd.authentication.commons.error.CustomServiceException;
import com.isd.authentication.domain.Balance;
import com.isd.authentication.domain.Transaction;
import com.isd.authentication.domain.User;
import com.isd.authentication.dto.TransactionRequestDTO;
import com.isd.authentication.dto.TransactionResponseDTO;
import com.isd.authentication.repository.BalanceRepository;
import com.isd.authentication.repository.TransactionRepository;
import com.isd.authentication.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class TransactionService {
    private final BalanceRepository br;
    private final TransactionRepository tr;
    private final UserRepository ur;

    Transaction transaction;
    Balance balance;

    public TransactionService(BalanceRepository br, TransactionRepository tr, UserRepository ur) {
        this.br = br;
        this.tr = tr;
        this.ur = ur;
    }

    private void processTransaction(TransactionRequestDTO request, TransactionType type) throws Exception {
        if (request.getAmount() <= 0 || request.getCircuit() == null || request.getCircuit() == null) {
            throw new CustomServiceException(new CustomHttpResponse(HttpStatus.BAD_REQUEST, "Bad request"));
        }
        User user = ur.findOneById(request.getUserId());
        if (user == null || user.getEnabled() == false) {
            throw new CustomServiceException(new CustomHttpResponse(HttpStatus.BAD_REQUEST, "User not found"));
        }

        balance = br.findByUserId(user.getId());
        if (balance == null) {
            throw new CustomServiceException(new CustomHttpResponse(HttpStatus.BAD_REQUEST, "Balance not found"));
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
            throw new CustomServiceException(new CustomHttpResponse(HttpStatus.BAD_REQUEST, "Insufficient founds on balance"));
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
