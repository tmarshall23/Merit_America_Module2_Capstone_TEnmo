package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal findAccountBalanceByUsername(String username) {

        String sql = "SELECT balance FROM account JOIN tenmo_user ON " +
                "account.user_id = tenmo_user.user_id" +
                " WHERE username ILIKE ? ";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, username);

    }

    @Override
    public BigDecimal findAccountBalanceByUserId(int id) {

        String sql = "SELECT balance FROM account WHERE user_id = ? ";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, id);

    }


    @Override
    public void withdrawBalanceById(int accountId, BigDecimal newTotal) {


//
//        String sql = "SELECT account_id, user_id, balance FROM account where user_id = ?;";
//        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, Account.class, userId);
//        account = mapRowToAccount(result);


        String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
        jdbcTemplate.update(sql, BigDecimal.class, newTotal, accountId);
    }


    @Override
    public void depositBalanceById(Long userId, BigDecimal depositAmount) {
        Account account = new Account();
        account.setAccount_id(userId);
        account.setBalance(depositAmount);
        String sql = "UPDATE account SET balance = ? WHERE user_id = ?";
        jdbcTemplate.queryForObject(sql, BigDecimal.class, account.getBalance(), account.getAccount_id());
    }


    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccount_id(rs.getLong("account_id"));
        account.setUser_id(rs.getLong("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
}