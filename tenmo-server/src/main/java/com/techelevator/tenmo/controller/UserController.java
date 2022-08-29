package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    JdbcUserDao dao;

    public UserController(JdbcUserDao dao) {
        this.dao = dao;
    }

    //Receives a List of usernames of all users
    @GetMapping("")
    public List<String> getUsers(){
        return dao.findAllUsername();
    }
    //Receives a users Id via the username
    @GetMapping("/{username}")
    public Integer getId(@Valid @PathVariable String username){
        return dao.findIdByUsername(username);
    }

}
