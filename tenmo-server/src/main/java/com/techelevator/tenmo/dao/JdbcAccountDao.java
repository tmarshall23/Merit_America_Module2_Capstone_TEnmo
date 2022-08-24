package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.LoginDTO;
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







}
