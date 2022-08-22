package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.LoginDTO;

import java.math.BigDecimal;

public interface AccountDao {


    BigDecimal findAccountByUsername(String username);
}
