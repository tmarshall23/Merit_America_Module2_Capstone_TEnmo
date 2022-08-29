package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //joins account to tenmo_user to get the balance by username
    @Override
    public BigDecimal findAccountBalanceByUsername(String username) {

        String sql = "SELECT balance FROM account JOIN tenmo_user ON " +
                "account.user_id = tenmo_user.user_id" +
                " WHERE username ILIKE ? ";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, username);

    }
    //Receives the balance from an account by user Id
    @Override
    public BigDecimal findAccountBalanceByUserId(int id) {

        String sql = "SELECT balance FROM account WHERE user_id = ? ";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, id);

    }
    //Updates accounts balance to new balance
    //used for withdraw and deposit
    @Override
    public int update(Account account, Long accountId) {
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
        return this.jdbcTemplate.update(sql, account.getBalance(), accountId);
    }
    //Receives entire account entity via user Id
    @Override
    public Account getAccountById(Long userId) throws AccountNotFoundException {
        Account account = new Account();
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.wasNull()){
            throw new AccountNotFoundException("Unable to retrieve account.");
            }
        while (results.next()) {
                account = mapRowToAccount(results);
            }
        return account;
    }
    // maps contents to account object
    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccount_id(rs.getLong("account_id"));
        account.setUser_id(rs.getLong("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
}