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

    private static final String API_BASE_URL = "http://localhost:8080/transfer";

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

    public HashMap<Long, Transfer> getTransfers(){

       HashMap<Long, Transfer> transfers;

            transfers = restTemplate.getForObject(API_BASE_URL + "/all", HashMap.class);

        return transfers;

    }

    //take serverside params and print them in corresponding order//

    public void printTransfersList(HashMap<Long, Transfer> transfers){

        for (HashMap.Entry<Long, Transfer> transfer : transfers.entrySet()){
            System.out.println(transfer.getKey() + " " + transfer.getValue());

        }


    }



    private HttpEntity<Transfer> makeEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authUser.getToken());
        return new HttpEntity<>(transfer, headers);
    }







    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authUser.getToken());
        return new HttpEntity<>(headers);
    }
}
