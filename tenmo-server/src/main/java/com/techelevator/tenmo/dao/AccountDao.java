package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.LoginDTO;

import java.math.BigDecimal;

public interface AccountDao {


    BigDecimal findAccountBalanceByUsername(String username);

    BigDecimal findAccountBalanceByUserId(int id);

    void withdrawBalanceById(int accountId, BigDecimal withdrawAmount);

    void depositBalanceById(Long accountId, BigDecimal depositAmount);
}
