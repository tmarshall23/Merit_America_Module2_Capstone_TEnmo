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

    @GetMapping("{transferId}")
    public Transfer getTransferById(@PathVariable Long transferId){
        return transferDao.getTransferForId(transferId);
    }

    @GetMapping("{statusId}/status")
    public String getStatusDescById(@PathVariable Integer statusId){
        return transferDao.getTransferStatusName(statusId);
    }

    @GetMapping("{typeId}/type")
    public String getTypeDescById(@PathVariable Integer typeId){
        return transferDao.getTransferTypeName(typeId);
    }

    @GetMapping("/from/{accountId}")
    public List<Long> getAllTransferIdsFrom(@PathVariable Long accountId){

        return transferDao.getTransferIdFrom(accountId);
    }
    @GetMapping("/to/{accountId}")
    public List<Long> getAllTransferIdsTo(@PathVariable Long accountId){

        return transferDao.getTransferIdTo(accountId);
    }

    @GetMapping("/{transferId}/to")
    public String getTransferToUsername(@Valid @PathVariable Long transferId){
        return transferDao.getTransferToUsername(transferId);
    }
    @GetMapping("/{transferId}/from")
    public String getTransferFromUsername(@Valid @PathVariable Long transferId){
        return transferDao.getTransferFromUsername(transferId);
    }


    @GetMapping("/amount/{transferId}")
    public BigDecimal getAmountForTransfer(@PathVariable Long transferId){
        return transferDao.getAmountForTransfer(transferId);
    }




}
