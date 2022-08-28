package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/transfer")
public class TransferController {

    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    @PostMapping("")
    public Transfer postTransfer(@RequestBody Transfer transfer){
       return transferDao.postTransfer(transfer);

    }

    @GetMapping("/all")
    public List<Transfer> getAllTransfers(){
        return transferDao.getAllTransfers();
    }

    @GetMapping("/{accountId}")
    public List<Long> getAllTransferIds(@PathVariable Long accountId){
        return transferDao.getTransferId(accountId);
    }

    @GetMapping("/id/{transferId}")
    public String getTransferToUsername(@Valid @PathVariable Long transferId){
        return transferDao.getTransferToUsername(transferId);
    }

    @GetMapping("/amount/{transferId}")
    public BigDecimal getAmountForTransfer(@PathVariable Long transferId){
        return transferDao.getAmountForTransfer(transferId);
    }




}
