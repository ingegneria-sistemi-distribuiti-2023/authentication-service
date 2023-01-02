package com.isd.authentication.converter;

import com.isd.authentication.domain.Transaction;
import com.isd.authentication.dto.TransactionDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionConverter {

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
