package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.LoginDTO;

import java.math.BigDecimal;

public interface AccountDao {


    BigDecimal findAccountByUsername(String username);

    BigDecimal findAccountById(int id);

    void withdrawBalanceById(Long accountId, BigDecimal withdrawAmount);

    void depositBalanceById(Long accountId, BigDecimal depositAmount);
}
