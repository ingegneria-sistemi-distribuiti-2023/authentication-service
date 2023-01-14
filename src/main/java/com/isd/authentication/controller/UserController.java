package com.isd.authentication.controller;

import com.isd.authentication.dto.*;
import com.isd.authentication.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
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
    public @ResponseBody
    UserBalanceTransDTO getUserById(@PathVariable("id") Integer userId) throws Exception{
        return umps.findUserById(userId);
    }

    @PostMapping(path="/delete")
    public @ResponseBody
    UserBalanceDTO del(@NotNull @RequestParam Integer userId) throws Exception {
        UserBalanceDTO toRet = null;

        try {
            toRet = umps.deleteUser(userId);
        } catch (Error e){
            new Exception(e.getMessage());
        }

        return toRet;
    }

}
