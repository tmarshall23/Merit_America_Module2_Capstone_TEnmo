package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
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

    private static final String API_BASE_URL = "http://localhost:8080/account/";

    private final RestTemplate restTemplate = new RestTemplate();

    private AuthenticatedUser authUser;

    public AccountService(){}

    public AccountService(AuthenticatedUser authUser) {
        this.authUser = authUser;
    }


    public BigDecimal findBalance(Long userId){
        BigDecimal output = null;
        try {
            ResponseEntity<BigDecimal> response =
                    restTemplate.getForEntity(API_BASE_URL + "id/" + userId, BigDecimal.class,
                            makeAuthEntity());
            output = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return output;
    }


    public String isTransferValid(BigDecimal transferAmount,Long userId){
        Boolean valid = false;
        if(findBalance(userId).compareTo(transferAmount) < 0){
            valid = false;
        }
        else if (findBalance(userId).compareTo(transferAmount) > 0){
            valid = true;
        }
        if(valid.equals(true)){
            return "Approved";
        }else{
            return "Transaction Denied";
        }
    }


    public void update(Account account, int accountId){

        restTemplate.put(API_BASE_URL + "transfer/" + accountId, account);

    }

    public Account getAccount(Long userId){

    return restTemplate.getForObject(API_BASE_URL + userId, Account.class);

    }



    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authUser.getToken());
        return new HttpEntity<>(headers);
    }

}






