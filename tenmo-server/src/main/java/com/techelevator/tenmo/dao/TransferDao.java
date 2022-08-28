package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {


    Transfer postTransfer(Transfer transfer);

    List<Transfer> getAllTransfers();

    Transfer getTransferForId(Long transferId);

    String getTransferStatusName(Integer statusId);

    String getTransferTypeName(Integer typeId);

    List<Long> getTransferIdFrom(Long accountId);

    List<Long> getTransferIdTo(Long accountId);

    String getTransferToUsername(Long accountId);

    String getTransferFromUsername(Long transferId);

    BigDecimal getAmountForTransfer(Long transferId);
}
