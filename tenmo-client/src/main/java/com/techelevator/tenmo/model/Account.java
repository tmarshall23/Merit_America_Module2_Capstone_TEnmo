package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    private Long user_id;
    private Long account_id;
    private BigDecimal balance;


    public Account(){}

    public Account(Long ser_id){}

    public Account(Long user_id, Long account_id, BigDecimal balance) {
        this.user_id = user_id;
        this.account_id = account_id;
        this.balance = balance;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


}
