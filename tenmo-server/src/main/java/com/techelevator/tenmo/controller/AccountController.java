package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.LoginDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.math.BigDecimal;



@RestController
@RequestMapping("/account")
public class AccountController {

    private AccountDao dao;

    public AccountController(AccountDao dao) {
        this.dao = dao;
    }


    @GetMapping("/{username}/balance")
    public BigDecimal findBalance(@Valid @PathVariable String username) throws UsernameNotFoundException {
        try {
            return dao.findAccountByUsername(username);
        }catch (UsernameNotFoundException e){
            System.out.println(e.getMessage());

        }
    return null;

    }





}
