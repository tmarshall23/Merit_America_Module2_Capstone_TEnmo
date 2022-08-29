package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.exception.TransferNotFoundException;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Transfer postTransfer(@RequestBody Transfer transfer){
       return transferDao.postTransfer(transfer);
    }

    @GetMapping("/all")
    public List<Transfer> getAllTransfers(){
        return transferDao.getAllTransfers();
    }

    @GetMapping("{transferId}")
    public Transfer getTransferById(@Valid @PathVariable Long transferId) throws TransferNotFoundException {
        return transferDao.getTransferForId(transferId);
    }

    @GetMapping("{statusId}/status")
    public String getStatusDescById(@Valid @PathVariable Integer statusId){
        return transferDao.getTransferStatusName(statusId);
    }

    @GetMapping("{typeId}/type")
    public String getTypeDescById(@Valid @PathVariable Integer typeId){
        return transferDao.getTransferTypeName(typeId);
    }

    @GetMapping("/from/{accountId}")
    public List<Long> getAllTransferIdsFrom(@Valid @PathVariable Long accountId){

        return transferDao.getTransferIdFrom(accountId);
    }
    @GetMapping("/to/{accountId}")
    public List<Long> getAllTransferIdsTo(@Valid @PathVariable Long accountId){

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
    public BigDecimal getAmountForTransfer(@Valid @PathVariable Long transferId){
        return transferDao.getAmountForTransfer(transferId);
    }

}
