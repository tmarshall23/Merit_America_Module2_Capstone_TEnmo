package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.LoginDTO;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

public interface AccountDao {


    BigDecimal findAccountBalanceByUsername(String username);

    BigDecimal findAccountBalanceByUserId(int id);

    int update(Account account, Long accountId);

    Account getAccountById(Long accountId) throws AccountNotFoundException;
}
