package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {


    private int transferType;
    private int transferStatus;
    private Long currentAccountId;
    private Long receivingAccountId;
    private BigDecimal transferAmount;

    public Transfer(int transferType, int transferStatus, Long currentAccountId, Long receivingAccountId, BigDecimal transferAmount) {
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.currentAccountId = currentAccountId;
        this.receivingAccountId = receivingAccountId;
        this.transferAmount = transferAmount;
    }

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public int getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(int transferStatus) {
        this.transferStatus = transferStatus;
    }

    public Long getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(Long currentAccountId) {
        this.currentAccountId = currentAccountId;
    }

    public Long getReceivingAccountId() {
        return receivingAccountId;
    }

    public void setReceivingAccountId(Long receivingAccountId) {
        this.receivingAccountId = receivingAccountId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }


}
