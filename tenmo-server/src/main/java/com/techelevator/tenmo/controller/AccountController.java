package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.LoginDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
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
    //Receives the balance of an Account via the Username
    @GetMapping("/{username}/balance")
    public BigDecimal findBalance(@Valid @PathVariable String username) throws UsernameNotFoundException {
        try {
            return dao.findAccountBalanceByUsername(username);
        }catch (UsernameNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    //Receives the balance of an Account via the userId
    @GetMapping("/id/{userId}")
    public BigDecimal findBalanceById(@Valid @PathVariable int userId) {
        return dao.findAccountBalanceByUserId(userId);
    }
    //updates the method of the account with the given info
    //used for withdraw and deposit
    @PutMapping({"/transfer/{accountId}"})
    public int update(@RequestBody Account account, @Valid @PathVariable Long accountId) {
        return this.dao.update(account, accountId);
    }
    //Receives the entire account entity via account Id
    @GetMapping("/{userId}")
    public Account getAccount(@Valid @PathVariable Long userId) throws AccountNotFoundException {
        return dao.getAccountById(userId);
    }

}