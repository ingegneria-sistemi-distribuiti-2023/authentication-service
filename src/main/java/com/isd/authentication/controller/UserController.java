package com.isd.authentication.controller;

import com.isd.authentication.dto.*;
import com.isd.authentication.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth/user")
public class UserController {

    @Autowired
    private UserService umps;

    @GetMapping(path="/")
    public @ResponseBody Iterable<UserBalanceDTO> getAllUsers() throws Exception{
        return umps.getAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody UserBalanceTransDTO getUserById(@PathVariable("id") Integer userId) throws Exception{
        return umps.findUserById(userId);
    }

    @GetMapping(path="/disable/{id}")
    public @ResponseBody UserBalanceDTO disableUser(@PathVariable("id") String userId) throws Exception {
        return umps.disableOrEnable(Integer.parseInt(userId), false);
    }

    @GetMapping(path="/enable/{id}")
    public ResponseEntity<UserBalanceDTO> enableUser(@PathVariable("id") Integer userId) throws Exception {
        UserBalanceDTO user = umps.disableOrEnable(userId, true);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
