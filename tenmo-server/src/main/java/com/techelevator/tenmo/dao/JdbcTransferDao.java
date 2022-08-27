package com.techelevator.tenmo.dao;



import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferDao implements TransferDao{


    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int postTransfer(Transfer transfer){

        int transferId;
        String sql = "INSERT INTO transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                "VALUES (?,?,?,?,?) RETURNING transfer_id;";
       transferId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransfer_type_id(),transfer.getTransfer_status_id(),
                            transfer.getAccount_from(),transfer.getAccount_to(),transfer.getAmount());
        return transferId;
    }
    //futrue selves, the sql is messed up somewhere, figure out how to instantiate a transfer ID check trasnfer class, maybe serial.













    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_type_id (rs.getInt("transfer_type_id"));
        transfer.setTransfer_status_id(rs.getInt("transfer_status_id"));
        transfer.setAccount_from(rs.getLong("account_from"));
        transfer.setAccount_to(rs.getLong("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }
}
