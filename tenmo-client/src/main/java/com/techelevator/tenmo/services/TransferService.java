package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

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

        restTemplate.exchange(API_BASE_URL, HttpMethod.POST, makeAuthEntity(), Void.class);

    }








    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authUser.getToken());
        return new HttpEntity<>(headers);
    }
}
