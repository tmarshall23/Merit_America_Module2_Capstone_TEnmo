package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {


    Transfer postTransfer(Transfer transfer);

    List<Transfer> getAllTransfers();

    List<Long> getTransferId(Long transferId);

    String getTransferToUsername(Long accountId);

    BigDecimal getAmountForTransfer(Long transferId);
}
