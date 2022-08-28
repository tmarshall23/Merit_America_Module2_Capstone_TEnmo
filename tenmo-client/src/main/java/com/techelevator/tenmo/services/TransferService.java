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

    public TransferService() {
    }

    public TransferService(AuthenticatedUser authUser) {
        this.authUser = authUser;
    }


    public void sendTransferInfo(Transfer transfer){

        HttpEntity<Transfer> entity = makeEntity(transfer);

        restTemplate.exchange(API_BASE_URL, HttpMethod.POST, entity, Transfer.class);

    }

    public List<Integer> getTransferIdsTo(Long accountId){
       return restTemplate.getForObject(API_BASE_URL + "from/" + accountId , List.class);

    }
    public List<Integer> getTransferIdsFrom(Long accountId){
        return restTemplate.getForObject(API_BASE_URL  + "to/" + accountId, List.class);

    }

    public String getUsernameForTransferTo(Integer transferId){
        return  restTemplate.getForObject(API_BASE_URL + transferId + "/to", String.class);
    }

    public String getUsernameForTransferFrom(Integer transferId){
        return  restTemplate.getForObject(API_BASE_URL + transferId + "/from", String.class);
    }

    public BigDecimal getAmountForTransfer(Integer transferId){
        return  restTemplate.getForObject(API_BASE_URL + "amount/" + transferId, BigDecimal.class);
    }

    public Transfer getTransferForId(Long transferId){
        return restTemplate.getForObject(API_BASE_URL + transferId, Transfer.class);
    }

    public String getTransferStatusDesc(Integer statusId){
        return restTemplate.getForObject(API_BASE_URL + statusId + "/status", String.class);
    }

    public String getTransferTypeDesc(Integer typeId){
        return restTemplate.getForObject(API_BASE_URL + typeId + "/type", String.class);
    }

    private HttpEntity<Transfer> makeEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authUser.getToken());
        return new HttpEntity<>(transfer, headers);
    }

}
