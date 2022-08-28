package com.techelevator.tenmo.dao;



import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{


    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Transfer postTransfer(Transfer transfer){

        Long transferId;
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                "VALUES (?,?,?,?,?) RETURNING transfer_id;";
       transferId = jdbcTemplate.queryForObject(sql, Long.class, transfer.getTransfer_type_id(),transfer.getTransfer_status_id(),transfer.getAccount_from(),transfer.getAccount_to(),transfer.getAmount());
       transfer.setTransfer_id(transferId);
        return transfer;
    }

    @Override
    public List<Transfer> getAllTransfers(){
        List<Transfer> transfers =  new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer";
        SqlRowSet result =jdbcTemplate.queryForRowSet(sql);
        while(result.next()){
            transfers.add(mapRowToTransfer(result));
        }
    return transfers;
    }

    @Override
    public Transfer getTransferForId(Long transferId){
        Transfer transfer = new Transfer();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer WHERE transfer_id = ?";
        SqlRowSet result =jdbcTemplate.queryForRowSet(sql, transferId);
        while (result.next()){
            transfer = mapRowToTransfer(result);
        }
        return transfer;
    }

    @Override
    public String getTransferStatusName(Integer statusId){

        String sql = "SELECT transfer_status_desc FROM transfer_status WHERE transfer_status_id = ?;";
        return jdbcTemplate.queryForObject(sql, String.class, statusId);
    }

    @Override
    public String getTransferTypeName(Integer typeId){

        String sql = "SELECT transfer_type_desc FROM transfer_type WHERE transfer_type_id = ?;";
        return jdbcTemplate.queryForObject(sql, String.class, typeId);
    }

        @Override
        public List<Long> getTransferIdFrom(Long accountId){
        List<Long> ids = new ArrayList<>();
        String sql = "SELECT transfer_id FROM transfer WHERE account_from = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
        while(result.next()){
            ids.add(result.getLong("transfer_id"));
        }
        return ids;
        }

    @Override
    public List<Long> getTransferIdTo(Long accountId){
        List<Long> ids = new ArrayList<>();
        String sql = "SELECT transfer_id FROM transfer WHERE account_to = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
        while(result.next()){
            ids.add(result.getLong("transfer_id"));
        }
        return ids;
    }


    @Override
    public String getTransferToUsername(Long transferId){

        String sql = "SELECT tenmo_user.username FROM transfer JOIN account ON transfer.account_to = account.account_id " +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                "WHERE transfer.transfer_id = ?";
       return jdbcTemplate.queryForObject(sql, String.class, transferId);
    }

    @Override
    public String getTransferFromUsername(Long transferId){

        String sql = "SELECT tenmo_user.username FROM transfer JOIN account ON transfer.account_from = account.account_id " +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                "WHERE transfer.transfer_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, transferId);
    }

    @Override
    public BigDecimal getAmountForTransfer(Long transferId){
        List<Long> ids = new ArrayList<>();
        String sql = "SELECT amount FROM transfer WHERE transfer_id = ?";
       return jdbcTemplate.queryForObject(sql,BigDecimal.class, transferId);

    }


    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(rs.getLong("transfer_id"));
        transfer.setTransfer_type_id (rs.getInt("transfer_type_id"));
        transfer.setTransfer_status_id(rs.getInt("transfer_status_id"));
        transfer.setAccount_from(rs.getLong("account_from"));
        transfer.setAccount_to(rs.getLong("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }
}
