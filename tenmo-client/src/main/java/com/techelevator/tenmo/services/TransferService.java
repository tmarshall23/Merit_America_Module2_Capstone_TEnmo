package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferService {

    private static final String API_BASE_URL = "http://localhost:8080/transfer/";

    private final RestTemplate restTemplate = new RestTemplate();

    private AuthenticatedUser authUser;

    public TransferService() {}

    public TransferService(AuthenticatedUser authUser) {
        this.authUser = authUser;
    }

    public void sendTransferInfo(Transfer transfer){

        HttpEntity<Transfer> entity = makeEntity(transfer);

        restTemplate.exchange(API_BASE_URL, HttpMethod.POST, entity, Transfer.class);

    }
    //Provides a list of Transfer ids for the receiving accounts
    public List<Integer> getTransferIdsTo(Long accountId) {
        List<Integer> output = null;
        try {
            output = restTemplate.getForObject(API_BASE_URL + "from/" + accountId, List.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return output;
    }
    //Provides a list of Transfer ids for the sending accounts
    public List<Integer> getTransferIdsFrom(Long accountId){
        List<Integer> output = null;
        try {
        output = restTemplate.getForObject(API_BASE_URL  + "to/" + accountId, List.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return output;
    }
    //Receives the username for transfer Ids provided for receiving account
    public String getUsernameForTransferTo(Integer transferId){
        String output = "";
        try {
        output = restTemplate.getForObject(API_BASE_URL + transferId + "/to", String.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return output;
    }
    //Receives the username for transfer Ids provided for sending account
    public String getUsernameForTransferFrom(Integer transferId){
        String output = "";
        try {
        output =  restTemplate.getForObject(API_BASE_URL + transferId + "/from", String.class);
    } catch (RestClientResponseException | ResourceAccessException e) {
        BasicLogger.log(e.getMessage());
    }
        return output;
    }
    //Receives amount for specific transfer for the Id
    public BigDecimal getAmountForTransfer(Integer transferId){
        BigDecimal output = null;
        try {
        output = restTemplate.getForObject(API_BASE_URL + "amount/" + transferId, BigDecimal.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return output;
    }

    //Returns all info for the transfer to display for specific id
    public Transfer getTransferForId(Long transferId){
        Transfer output = null;
        try {
        output = restTemplate.getForObject(API_BASE_URL + transferId, Transfer.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return output;
    }
    //Returns the Status Description of a transfer_status
    public String getTransferStatusDesc(Integer statusId){
        String output = "";
        try {
        output = restTemplate.getForObject(API_BASE_URL + statusId + "/status", String.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return output;
    }
    //Returns the Type Description of a transfer_type
    public String getTransferTypeDesc(Integer typeId){
        String output = "";
        try {
        output = restTemplate.getForObject(API_BASE_URL + typeId + "/type", String.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return output;
    }

    private HttpEntity<Transfer> makeEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authUser.getToken());
        return new HttpEntity<>(transfer, headers);
    }

}