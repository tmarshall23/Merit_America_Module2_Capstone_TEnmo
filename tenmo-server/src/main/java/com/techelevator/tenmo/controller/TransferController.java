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
    //Creates an entire new transfer in the database
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Transfer postTransfer(@RequestBody Transfer transfer){
       return transferDao.postTransfer(transfer);
    }
    //Receives a list of all transfers and their information
    @GetMapping("/all")
    public List<Transfer> getAllTransfers(){
        return transferDao.getAllTransfers();
    }
    //Receives a single transfer via the transfer Id
    @GetMapping("{transferId}")
    public Transfer getTransferById(@Valid @PathVariable Long transferId) throws TransferNotFoundException {
        return transferDao.getTransferForId(transferId);
    }
    //Receives the status description from transfer_status via status Id
    @GetMapping("{statusId}/status")
    public String getStatusDescById(@Valid @PathVariable Integer statusId){
        return transferDao.getTransferStatusName(statusId);
    }
    //Receives the type description from transfer_status via type Id
    @GetMapping("{typeId}/type")
    public String getTypeDescById(@Valid @PathVariable Integer typeId){
        return transferDao.getTransferTypeName(typeId);
    }
    //Receives a list of transfer Ids for a given receiving account
    @GetMapping("/from/{accountId}")
    public List<Long> getAllTransferIdsFrom(@Valid @PathVariable Long accountId){

        return transferDao.getTransferIdFrom(accountId);
    }
    //Receives a list of transfer Ids for a given sending account
    @GetMapping("/to/{accountId}")
    public List<Long> getAllTransferIdsTo(@Valid @PathVariable Long accountId){

        return transferDao.getTransferIdTo(accountId);
    }
    //Receives a given receiving username via transfer Id
    @GetMapping("/{transferId}/to")
    public String getTransferToUsername(@Valid @PathVariable Long transferId){
        return transferDao.getTransferToUsername(transferId);
    }
    //Receives a given sending username via transfer Id
    @GetMapping("/{transferId}/from")
    public String getTransferFromUsername(@Valid @PathVariable Long transferId){
        return transferDao.getTransferFromUsername(transferId);
    }
    //Receives amount transferred via transfer Id
    @GetMapping("/amount/{transferId}")
    public BigDecimal getAmountForTransfer(@Valid @PathVariable Long transferId){
        return transferDao.getAmountForTransfer(transferId);
    }

}
