package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {


    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal findAccountByUsername(String username){

        String sql = "SELECT balance FROM account JOIN tenmo_user ON " +
                "account.user_id = tenmo_user.user_id" +
                " WHERE username ILIKE ? ";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, username);

    }

    @Override
    public BigDecimal findAccountById(int id){

        String sql = "SELECT balance FROM account WHERE user_id = ? ";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, id);

    }

    @Override
    public void withdrawBalanceById(Long accountId, BigDecimal withdrawAmount){
    Account account = new Account();
    account.setAccount_id(accountId);
    BigDecimal updatedBalance = account.getBalance().subtract(withdrawAmount);
    String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
    jdbcTemplate.update(sql, BigDecimal.class, updatedBalance, account.getAccount_id());


    }

    @Override
    public void depositBalanceById(Long accountId, BigDecimal depositAmount){
        Account account = new Account();
        account.setAccount_id(accountId);
        account.setBalance(depositAmount);
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
        jdbcTemplate.queryForObject(sql, BigDecimal.class, account.getBalance(), account.getAccount_id());
    }

}
