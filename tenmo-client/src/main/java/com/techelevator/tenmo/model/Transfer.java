package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {


    private Long transfer_id;
    private int transfer_type_id;
    private int transfer_status_id;
    private Long account_from;
    private Long account_to;
    private BigDecimal amount;

    public Transfer() {
    }

    public Transfer(int transferType, int transferStatus, Long currentAccountId, Long receivingAccountId, BigDecimal transferAmount) {
        this.transfer_type_id = transferType;
        this.transfer_status_id = transferStatus;
        this.account_from = currentAccountId;
        this.account_to = receivingAccountId;
        this.amount = transferAmount;
    }

    public Long getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(Long transfer_id) {
        this.transfer_id = transfer_id;
    }

    public int getTransfer_type_id() {
        return transfer_type_id;
    }

    public void setTransfer_type_id(int transfer_type_id) {
        this.transfer_type_id = transfer_type_id;
    }

    public int getTransfer_status_id() {
        return transfer_status_id;
    }

    public void setTransfer_status_id(int transfer_status_id) {
        this.transfer_status_id = transfer_status_id;
    }

    public Long getAccount_from() {
        return account_from;
    }

    public void setAccount_from(Long account_from) {
        this.account_from = account_from;
    }

    public Long getAccount_to() {
        return account_to;
    }

    public void setAccount_to(Long account_to) {
        this.account_to = account_to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


}
