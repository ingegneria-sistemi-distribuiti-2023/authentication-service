package com.isd.authentication.controller;

import com.isd.authentication.dto.TransactionRequestDTO;
import com.isd.authentication.dto.TransactionResponseDTO;
import com.isd.authentication.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService tr;

    @PostMapping(path="/deposit")
    @SecurityRequirement(name = "bearerAuth")
    public @ResponseBody
    ResponseEntity<TransactionResponseDTO> deposit(@RequestHeader("Secret-Key") String secret, @RequestBody TransactionRequestDTO request) throws Exception {
        return new ResponseEntity<>(tr.deposit(request), HttpStatus.OK);
    }

    @PostMapping(path="/withdraw")
    @SecurityRequirement(name = "bearerAuth")
    public @ResponseBody ResponseEntity<TransactionResponseDTO> withdraw(@RequestHeader("Secret-Key") String secret, @RequestBody TransactionRequestDTO request) throws Exception {
        return new ResponseEntity<>(tr.withdraw(request), HttpStatus.OK);
    }

}
