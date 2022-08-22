package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final RestTemplate restTemplate = new RestTemplate();

    private AuthenticatedUser authUser;

    public AccountService(AuthenticatedUser authUser) {
        this.authUser = authUser;
    }

    public BigDecimal findBalance(String username){
        BigDecimal output = null;
        try {
            ResponseEntity<BigDecimal> response =
                    restTemplate.getForEntity(API_BASE_URL + "/account/" + username + "/balance", BigDecimal.class,
                            makeAuthEntity());
            output = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return output;
    }

    public String isTransferValid(BigDecimal transferAmount,String username){
        Boolean valid = false;
        if(findBalance(username).compareTo(transferAmount) < 0){
            valid = false;
        }
        else if (findBalance(username).compareTo(transferAmount) > 0){
            valid = true;
        }
        if(valid.equals(true)){
            return "Approved";
        }else{
            return "Transaction Denied";
        }
    }



    public Long getTransferToId(String username){

        Long output = null;
        try {
            ResponseEntity<Long> response =
                    restTemplate.getForEntity(API_BASE_URL + "/account/" + username , Long.class,
                            makeAuthEntity());
            output = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }


        return output;
    }

    public void transfer(String username1, String username2, BigDecimal transferAmount){
        withdraw(username1, transferAmount);
        deposit(username2, transferAmount);
    }


    public void withdraw(String username, BigDecimal withdrawAmount){

        BigDecimal balance = findBalance(username);
        balance = balance.subtract(withdrawAmount);

        restTemplate.put(API_BASE_URL + "/account/" + username + "/balance", balance);

    }

    public void deposit(String username, BigDecimal depositAmount){

        BigDecimal balance = findBalance(username);
        balance = balance.add(depositAmount);

        restTemplate.put(API_BASE_URL + "/account/" + username + "/balance", balance);

    }



    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authUser.getToken());
        return new HttpEntity<>(headers);
    }

}






