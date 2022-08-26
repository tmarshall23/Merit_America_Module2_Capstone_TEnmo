package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserService {



    private static final String API_BASE_URL = "http://localhost:8080/users/";

    private final RestTemplate restTemplate = new RestTemplate();

    private AuthenticatedUser authUser;

    public UserService(AuthenticatedUser authUser) {
        this.authUser = authUser;
    }




    public List<String> getUsers(){
        List<String> users = null;
        try {
            users = restTemplate.getForObject(API_BASE_URL, List.class);

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return users;
    }



    public HashMap<Long, String> userIdAndName(AuthenticatedUser authUser){

        HashMap<Long, String> outputMap = new HashMap<>();


        List<String> usernames = getUsers();
        removeUser(authUser.getUser().getUsername(), usernames);

        for (String username : usernames) {
            Long userId = getTransferToId(username);
            outputMap.put(userId, username);
        }

        return outputMap;
    }

    public Long getTransferToId(String username){

        Long output = null;
        try {
            ResponseEntity<Long> response =
                    restTemplate.getForEntity(API_BASE_URL + username , Long.class,
                            makeAuthEntity());
            output = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }


        return output;
    }



    public void removeUser(String username, List<String> usernames){
        for (int i = 0; i < usernames.size(); i++) {
            if(usernames.get(i).equals(username)){
                usernames.remove(username);
            }
        }
    }

  public void printUsers(HashMap<Long, String> usernames){

      for (HashMap.Entry<Long, String> username : usernames.entrySet()) {
          System.out.println(username.getKey() + " ---- " + username.getValue());
      }

  }




    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authUser.getToken());
        return new HttpEntity<>(headers);
    }

}














