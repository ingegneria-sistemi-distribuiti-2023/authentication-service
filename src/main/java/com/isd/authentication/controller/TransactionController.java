package com.isd.authentication.controller;


import com.isd.authentication.commons.TransactionStatus;
import com.isd.authentication.dto.TransactionRequestDTO;
import com.isd.authentication.dto.TransactionResponseDTO;
import com.isd.authentication.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/auth/transaction")
public class TransactionController {

    @Autowired
    TransactionService tr;

    @PostMapping(path="/deposit")
    public @ResponseBody TransactionResponseDTO deposit(@RequestBody TransactionRequestDTO request) throws Exception {
        TransactionResponseDTO toRet = null;

        try {
            toRet = tr.deposit(request);
        } catch (Error e){

            toRet.setMessage(e.getMessage());
            toRet.setTime(new Date());
            toRet.setStatus(TransactionStatus.ERROR);
        }


        return toRet;
    }

    // TODO: AL MOMENTO SOLO PER TEST
    @PostMapping(path="/withdraw")
    public @ResponseBody TransactionResponseDTO withdraw(@RequestBody TransactionRequestDTO request) throws Exception {
        TransactionResponseDTO toRet = null;

        try {
            toRet = tr.withdraw(request);
        } catch (Error e){

            toRet.setMessage(e.getMessage());
            toRet.setTime(new Date());
            toRet.setStatus(TransactionStatus.ERROR);
        }


        return toRet;
    }

}
