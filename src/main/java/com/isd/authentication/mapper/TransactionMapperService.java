package com.isd.authentication.mapper;

import com.isd.authentication.domain.Balance;
import com.isd.authentication.domain.Transaction;
import com.isd.authentication.domain.User;
import com.isd.authentication.dto.TransactionDTO;
import com.isd.authentication.dto.UserBalanceDTO;
import com.isd.authentication.repository.BalanceRepository;
import com.isd.authentication.repository.TransactionRepository;
import com.isd.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionMapperService {

    public TransactionDTO convertToDTO(Transaction transaction){
        TransactionDTO dto = new TransactionDTO();

        dto.setAmount(transaction.getAmount());
        dto.setCircuit(transaction.getCircuit());
        dto.setDate(transaction.getDate());
        dto.setUserId(transaction.getUserId());
        dto.setStatus(transaction.getStatus());

        return dto;
    }
}
