package com.isd.authentication.controller;

import com.isd.authentication.domain.User;
import com.isd.authentication.dto.UserBalanceTransactionDTO;
import com.isd.authentication.dto.UserCreateDTO;
import com.isd.authentication.mapper.UserMapperService;
import com.isd.authentication.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapperService umps;

    // FIXME: conviene utilizzare lo userService nello User Controller ? Credo non sia necessario
    // Dato che abbiamo già usermapperService però chiedi conferma, moolto probabilmente sono la stessa cosa !!!
//    @Autowired
//    private UserService us;

    @GetMapping(path="/")
    public @ResponseBody Iterable<UserBalanceTransactionDTO> getAllUsers(){
        return umps.getAll();
    }


    @PostMapping(path="/create")
    public @ResponseBody UserBalanceTransactionDTO create(@RequestBody UserCreateDTO body) throws Exception {
        UserBalanceTransactionDTO toRet = null;

        try {
            toRet = umps.createUser(body);
        } catch (Error e){
            new Exception(e.getMessage());
        }


        return toRet;
    }


    @PostMapping(path="/delete")
    public @ResponseBody UserBalanceTransactionDTO del(@NotNull @RequestBody UserCreateDTO body) throws Exception {
        UserBalanceTransactionDTO toRet = null;

        try {
            toRet = umps.deleteUser(body.getUsername());
        } catch (Error e){
            new Exception(e.getMessage());
        }

        return toRet;
    }

}
