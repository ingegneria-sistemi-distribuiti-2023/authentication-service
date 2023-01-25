package com.isd.authentication.controller;

import com.isd.authentication.dto.*;
import com.isd.authentication.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/auth/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService umps;

    @GetMapping(path="/")
    @SecurityRequirement(name = "bearerAuth")
    public @ResponseBody ResponseEntity<List<UserBalanceDTO>> getAllUsers(@RequestHeader("Secret-Key") String secret) throws Exception{
        return new ResponseEntity<List<UserBalanceDTO>>(umps.getAll(), HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public @ResponseBody ResponseEntity<UserBalanceTransDTO> getUserById(@RequestHeader("Secret-Key") String secret, @PathVariable("id") Integer userId) throws Exception{
        return new ResponseEntity<UserBalanceTransDTO>(umps.findUserById(userId), HttpStatus.OK);
    }

    @GetMapping(path="/disable/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public @ResponseBody ResponseEntity<UserBalanceDTO> disableUser(@RequestHeader("Secret-Key") String secret, @PathVariable("id") Integer userId) throws Exception {
        return new ResponseEntity<UserBalanceDTO>(umps.disableOrEnable(userId, false), HttpStatus.OK);
    }

    @GetMapping(path="/enable/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public @ResponseBody ResponseEntity<UserBalanceDTO> enableUser(@RequestHeader("Secret-Key") String secret, @PathVariable("id") Integer userId) throws Exception {
        return new ResponseEntity<UserBalanceDTO>(umps.disableOrEnable(userId, true), HttpStatus.OK);
    }
}
